//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starvel.common.util.DesUtil;
import com.starvel.forum.data.po.BasicUser;
import com.starvel.forum.service.CookieService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 教务系统工具
 * 辣鸡教务老是炸，所以里面加上了切换URL的逻辑
 *
 * @author Zsj
 */
@Component
public class AcademicUtil {
    /**
     * 本科教务系统的域名
     */
    private static String host = "http://bkjws.sdu.edu.cn";
    /**
     * 学生信息的URL
     */
    private static final String INFO_URL = "/b/grxx/xs/xjxx/detail";
    /**
     * 教师信息的URL
     */
    private static final String TEACHER_URL = "/b/jxrl/js/jslxfsb/detail/";
    /**
     * 信息服务中心的个人信息URL
     */
    private static final String SERVICE_MY_INFO = "http://service.sdu.edu.cn/tp_up/sys/uacm/profile/getUserById";

    @Autowired
    private CookieService cookieService;

    /**
     * 空的构造方法
     */
    public AcademicUtil() {

    }

    /**
     * 获取用户信息
     * <p>
     * 获取的流程：
     * </p>
     * <ol>
     *     <li>先从{@link AcademicUtil#host}尝试获取信息</li>
     *     <li>若{@link AcademicUtil#host}失效，则尝试教师端的教务系统</li>
     *     <li>若无法从教务系统获取到信息，则访问{@link AcademicUtil#getInfoByServiceCenter(Long, String, String)}方法来获取</li>
     * </ol>
     *
     * @param id i山大系统用户ID
     * @param u  学工号
     * @param p  统一认证密码
     * @return {@link BasicUser}类型的用户
     */
    public BasicUser getUserInformation(Long id, String u, String p) {
        String cookie = cookieService.getEduCookie(u, p);
        try {
            String body = Jsoup
                    .connect(host + INFO_URL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .header("Cookie", cookie)
                    .method(Connection.Method.POST)
                    .execute()
                    .body();

            if (body.contains("禁止访问")) {
                System.out.println(u + " fail to teacher login");
                // Teacher
                body = Jsoup
                        .connect(host + TEACHER_URL + u)
                        .ignoreContentType(true)
                        .header("Cookie", cookie)
                        .method(Connection.Method.POST)
                        .execute()
                        .body();

                JSONObject object = JSON.parseObject(body).getJSONObject("object");

                return new BasicUser(
                        id, u, null,
                        object.getString("jsm"),
                        object.getString("xb"),
                        object.getString("xsm"),
                        null, null
                );
            } else {
                System.out.println(u + " success to student");
                JSONObject object = JSON.parseObject(body).getJSONObject("object");

                return new BasicUser(
                        id, u, null,
                        object.getString("xm"),
                        object.getString("xb"),
                        object.getString("xsm"),
                        null, null
                );
            }
        } catch (IOException e) {
            e.printStackTrace();

            // 这个地方并发带来的线程不安全问题影响不是很大，所以不需要加锁
            if (!"http://bkjwt.sdu.edu.cn".equals(host)) {
                host = "http://bkjwt.sdu.edu.cn";
                return getUserInformation(id, u, p);
            }

            return null;
        } catch (Exception e) {
            // 此处会捕获到JSON解析的异常
            // 也就是教务系统无法获取用户信息了
            // 转向信息服务中心去获取
            System.out.println(u + " fail to service center login\n" + e.getLocalizedMessage());
            return getInfoByServiceCenter(id, u, p);
        }
    }

    /**
     * 从信息服务中心获取用户信息
     *
     * @param id i山大系统用户ID
     * @param u  学工号
     * @param p  统一认证密码
     * @return {@link BasicUser}类型的用户
     */
    public BasicUser getInfoByServiceCenter(Long id, String u, String p) {
        String cookie = cookieService.getServiceCookie(u, p);
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
            String body = client.newCall(
                    new Request.Builder()
                            .url(SERVICE_MY_INFO)
                            .header("Cookie", cookie)
                            .header("Content-Type", "application/json;charset=UTF-8")
                            .post(RequestBody.create("{\"BE_OPT_ID\":\"" +
                                            DesUtil.getInstance().strEnc(u, "tp", "des", "param") + "\"}",
                                    MediaType.parse("application/json; charset=utf-8")))
                            .build()
            ).execute().body().string();

            JSONObject obj = JSON.parseObject(body);
            return new BasicUser(
                    id, u, null,
                    obj.getString("USER_NAME"),
                    obj.getString("USER_SEX"),
                    obj.getString("UNIT_NAME"),
                    null, null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
