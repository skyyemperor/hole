//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas.sites;


import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.common.util.LockUtil;

import java.util.Arrays;
import java.util.HashSet;

public class ServiceCasLogin extends AbstractCasLogin {

    private static final String CAS_LOGIN_URL = "https://pass.sdu.edu.cn/cas/login?service=https%3A%2F%2Fservice.sdu.edu.cn%2Ftp_up%2Fview%3Fm%3Dup";
    private static final String HOST = "https://service.sdu.edu.cn";

    public ServiceCasLogin() {
        super();
    }

    public ServiceCasLogin(LockUtil lockUtil) {
        super(lockUtil);
    }

    @Override
    protected String getCasLoginUrl() {
        return CAS_LOGIN_URL;
    }

    @Override
    protected String getOriginHost() {
        return HOST;
    }

    @Override
    protected HashSet<String> getNeedCookiePrefix() {
        return new HashSet<>(Arrays.asList(
                "JSESSIONID",
                "cookie-adx"
        ));
    }
}
