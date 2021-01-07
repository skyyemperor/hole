//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas;

import com.starvel.common.util.CookieUtil;
import com.starvel.common.util.LockUtil;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * CAS登录操作
 * 可以继承该类来实现不同网站COOKIE的获取
 */
public abstract class AbstractCasLogin {

    private static final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS);

    private final CookieUtil cookieUtil = new CookieUtil();

    private String host = getOriginHost();

    /**
     * 最大重定向次数
     */
    private Integer maxRedirectCnt = 10;

    /**
     * 判断密码是否正确
     */
    private boolean error = false;

    /**
     * 获取CAS登录地址
     * 一般是<pre>https://pass.sdu.edu.cn/cas/login?service=xxx</pre>
     * <br>
     * 重写该方法返回特定服务的登录地址
     *
     * @return CAS登录地址
     */
    protected abstract String getCasLoginUrl();

    /**
     * 获取HOST域名或ip
     *
     * @return 域名
     */
    protected abstract String getOriginHost();

    /**
     * 返回CAS登录过程中需要的Cookie的前缀
     */
    protected abstract HashSet<String> getNeedCookiePrefix();

    protected String getLockName(String u) {
        String site = getCasLoginUrl();
        site = site.substring(site.indexOf("="));
        return site + "-" + u;
    }

    public String getSiteName() {
        String site = getCasLoginUrl();
        return site.substring(site.indexOf("=") + 1);
    }

    private LockUtil lockUtil;

    protected AbstractCasLogin() {
    }

    public AbstractCasLogin(LockUtil lockUtil) {
        this.lockUtil = lockUtil;
    }

    /**
     * 获取Cookie
     * 使用{@link OkHttpClient}进行网络的访问。并使用{@link RedirectInterceptor}进行跳转拦截，以模拟CAS登录。
     *
     * @param u 学工号
     * @param p 统一认证密码
     * @return 登录该服务的Cookie。如果获取不到则返回<pre>null</pre>
     * @throws IOException OkHttp错误
     */
    public String serve(String u, String p) throws IOException {
        OkHttpClient client = clientBuilder
                .build();

        Request requestHtml = new Request.Builder()
                .url(getCasLoginUrl())
                .get()
                .build();

        Response responseHtml = client.newCall(requestHtml).execute();
        cookieUtil.addCookie(responseHtml, getNeedCookiePrefix());

        Document document = Jsoup.parse(responseHtml.body().string());

        // 获取登录页面的LoginTicket
        String lt = document.getElementById("lt").val();

        String rsa = Des.strEnc(u + p + lt, "1", "2", "3");

        RequestBody body = new FormBody.Builder()
                .add("rsa", rsa)
                .add("ul", u.length() + "")
                .add("pl", p.length() + "")
                .add("lt", lt)
                .add("execution", "e1s1")
                .add("_eventId", "submit")
                .build();

        Request request = new Request.Builder()
                .url(getCasLoginUrl())
                .post(body)
                .addHeader("Cookie", cookieUtil.getCookie(getCasLoginUrl()))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        // 使用分布式锁保证登录不冲突
        if (lockUtil != null) {
            lockUtil.lockOrWait(getLockName(u));
        }

        try {
            client = client.newBuilder()
                    .addInterceptor(new RedirectInterceptor())
                    .build();
            client.newCall(request).execute();
        } catch (Exception e) {
            // Do nothing
        }

        if (lockUtil != null) {
            lockUtil.releaseKey(getLockName(u));
        }

        String endCookie = cookieUtil.getCookie(host);
        if (!error && !"".equals(endCookie)) {
            // 用户密码正确并且cookie不为""
            return endCookie;
        } else {
            // 防止Test中出现Cookie异常，所以用字符串的null
            return "null";
        }
    }

    /**
     * 重定向拦截器
     */
    class RedirectInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            cookieUtil.addCookie(response, getNeedCookiePrefix());

            // 如果需要重定向，则进行跳转拦截
            while (response.code() > 300 && maxRedirectCnt-- > 0) {
                error = error || response.body().string().contains("id=\"errormsg\"");
                if (error) {
                    // 用户名密码错误
                    break;
                }

                //处理location
                String location = response.header("Location");
                if (location == null || "".equals(location) || location.startsWith("http://pass.sdu.edu.cn") || location.startsWith("https://pass.sdu.edu.cn")) {
                    break;
                } else if (!location.startsWith("http")) {
                    if (location.startsWith("/")) {
                        location = getOriginHost() + location;
                    } else {
                        String lastUrl = response.request().url().toString();
                        location = lastUrl.substring(0, lastUrl.lastIndexOf("/") + 1) + location;
                    }
                }

                Request newRequest = request.newBuilder()
                        .header("Cookie", cookieUtil.getCookie(location))
                        .header("Referer", "https://pass.sdu.edu.cn/")
                        .get().url(location).build();

                response.close();
                response = chain.proceed(newRequest);
                cookieUtil.addCookie(response, getNeedCookiePrefix());
            }

            try {
                error = error || response.body().string().contains("id=\"errormsg\"");
                response.close();
            } catch (IllegalStateException e) {
                // 防止response关闭
            }

            //最后重定向的页面，将host设为此URL，便于最后返回最新路径的cookie
            host = response.request().url().toString();
            return response;
        }
    }
}
