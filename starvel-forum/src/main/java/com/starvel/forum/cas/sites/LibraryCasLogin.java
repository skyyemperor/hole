//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas.sites;


import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.common.util.LockUtil;

import java.util.Arrays;
import java.util.HashSet;

public class LibraryCasLogin extends AbstractCasLogin {

    private static final String CAS_LOGIN_URL = "http://pass.sdu.edu.cn/cas/login?service=http%3A%2F%2F58.194.172.34%2Freader%2Fhwthau.php";
    private static final String HOST = "http://58.194.172.34";

    public LibraryCasLogin() {
        super();
    }

    public LibraryCasLogin(LockUtil lockUtil) {
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
                "PHPSESSID",
                "JSESSIONID"
        ));
    }
}
