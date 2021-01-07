//
// Copyright (c) 2020 山东大学学生在线. All rights reserved.
//

package com.starvel.forum.cas.sites;


import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.common.util.LockUtil;

import java.util.Arrays;
import java.util.HashSet;

public class DataCenterCasLogin extends AbstractCasLogin {

    private static final String CAS_LOGIN_URL = "https://pass.sdu.edu.cn/cas/login?service=http%3A%2F%2Fservicedesk.sdu.edu.cn%2Fidc%2Findex.jsp";
    private static final String HOST = "http://servicedesk.sdu.edu.cn";

    public DataCenterCasLogin() {
        super();
    }

    public DataCenterCasLogin(LockUtil lockUtil) {
        super(lockUtil);
    }

    @Override
    protected String getCasLoginUrl() {
        return CAS_LOGIN_URL;
    }

    @Override
    protected String getOriginHost() {
        return null;
    }

    @Override
    protected HashSet<String> getNeedCookiePrefix() {
        return new HashSet<>(Arrays.asList(
                "JSESSIONID",
                "cookie-adx",
                "key_idc_v7"
        ));
    }

}
