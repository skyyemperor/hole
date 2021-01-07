//
// Copyright (c) 2019 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas.sites;


import com.starvel.common.util.LockUtil;
import com.starvel.forum.cas.AbstractCasLogin;

import java.util.Arrays;
import java.util.HashSet;

public class CourseSelectCasLogin extends AbstractCasLogin {

    private static final String CAS_LOGIN_URL = "http://passt.sdu.edu.cn/cas/login?service=http://bkjwxk.sdu.edu.cn/f/j_spring_security_thauth_roaming_entry";
    private static final String HOST = "http://bkjwxk.sdu.edu.cn";

    public CourseSelectCasLogin() {
        super();
    }

    public CourseSelectCasLogin(LockUtil lockUtil) {
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
                "sduxk"
        ));
    }
}
