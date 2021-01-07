package com.starvel.common.util;

import okhttp3.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by skyyemperor on 2020-11-27 11:42
 * Description : 存放cookie的工具类
 */
public class CookieUtil {
    private Map<String, HashMap<String, String>> cookieList = new HashMap<>();

    public void addCookie(Response response, HashSet<String> needCookies) {
        for (String cookie : response.headers("Set-Cookie")) {
            Matcher matcher = Pattern.compile("^((.+)=.+;).+((?i)Path)=(.+?);").matcher(cookie + ";");
            if (matcher.find() && needCookies.contains(matcher.group(2))) {
                //匹配的样例：JSESSIONID=C030F6B4FF2E077E371; Path=/cas; HttpOnly
                //group(1):JSESSIONID=C030F6B4FF2E077E371
                //group(2):JSESSIONID
                //group(4):/cas
                String path = response.request().url().host() + matcher.group(4);
                HashMap<String, String> cookies = cookieList.getOrDefault(path, new HashMap<>());
                cookies.put(matcher.group(2), matcher.group(1));
                cookieList.put(path, cookies);
            }
        }
    }

    public String getCookie(String path) {
        StringBuilder cookie = new StringBuilder();
        for (Map.Entry<String, HashMap<String, String>> entry : cookieList.entrySet()) {
            if (path.startsWith("http://" + entry.getKey()) || path.startsWith("https://" + entry.getKey())) {
                HashMap<String, String> cookies = entry.getValue();
                for (Map.Entry<String, String> entry2 : cookies.entrySet()) {
                    cookie.append(entry2.getValue());
                }
            }
        }
        return cookie.toString();
    }
}
