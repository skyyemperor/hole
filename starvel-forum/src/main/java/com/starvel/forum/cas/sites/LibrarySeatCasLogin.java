package com.starvel.forum.cas.sites;


import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.common.util.LockUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by skyyemperor on 2020-11-25 0:01
 * Description :
 */
public class LibrarySeatCasLogin extends AbstractCasLogin {
    private static final String CAS_LOGIN_URL = "http://pass.sdu.edu.cn/cas/login?service=http%3A%2F%2Fseat.lib.sdu.edu.cn%2Fcas%2Findex.php%3Fcallback%3Dhttp%3A%2F%2Fseat.lib.sdu.edu.cn%2Fhome%2Fweb%2Ff_second";
    private static final String HOST = "http://seat.lib.sdu.edu.cn";

    public LibrarySeatCasLogin() {
        super();
    }

    public LibrarySeatCasLogin(LockUtil lockUtil) {
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
                "JSESSIONID",
                "userid",
                "access_token"
        ));
    }
}
