//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas.sites;


import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.common.util.LockUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class EduCasLogin extends AbstractCasLogin {

    private String host = "http://bkjws.sdu.edu.cn";
    private static final String CAS_LOGIN_URL = "http://pass.sdu.edu.cn/cas/login?service=%s/f/j_spring_security_thauth_roaming_entry";

    public EduCasLogin() {
        super();
    }

    public EduCasLogin(LockUtil lockUtil) {
        super(lockUtil);
    }

    private boolean testOriginalHost() {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(host)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
        } catch (IOException e) {
            return false;
        }
        return response.statusCode() != 404;
    }

    private void reverseHost() {
        if ("http://bkjws.sdu.edu.cn".equals(host)) {
            host = "http://bkjwt.sdu.edu.cn";
        } else {
            host = "http://bkjws.sdu.edu.cn";
        }
    }

    @Override
    protected String getCasLoginUrl() {
        return String.format(CAS_LOGIN_URL, host);
    }

    @Override
    public String serve(String u, String p) throws IOException {
        host = "http://bkjws.sdu.edu.cn";
        if (!testOriginalHost()) {
            reverseHost();
        }
        return super.serve(u, p) + host.substring(11, 12);
    }

    @Override
    protected String getOriginHost() {
        return host;
    }

    @Override
    protected HashSet<String> getNeedCookiePrefix() {
        return new HashSet<>(Arrays.asList(
                "JSESSIONID"
        ));
    }
}
