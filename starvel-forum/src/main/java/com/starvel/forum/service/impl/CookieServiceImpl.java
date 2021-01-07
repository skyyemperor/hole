package com.starvel.forum.service.impl;

import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.forum.cas.sites.*;
import com.starvel.common.constant.BaseValue;
import com.starvel.common.util.LockUtil;
import com.starvel.common.util.RedisUtil;
import com.starvel.forum.service.CookieService;
import com.starvel.forum.data.po.BasicCasUser;
import com.starvel.forum.mapper.BasicCasUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by skyyemperor on 2020-12-25 1:19
 * Description :
 */
@Service
public class CookieServiceImpl implements CookieService {
    @Autowired
    private LockUtil lockUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BasicCasUserMapper casMapper;

    private static final String COOKIE_USER_KEY = BaseValue.HOLE_SYSTEM_NAME + ":COOKIE:SITE:%s:USER:%d";
    private static final long COOKIE_EXPIRE_TIME = 10 * 60 * 1000L;

    public String getEduCookie(String u, String p) {
        try {
            return new EduCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServiceCookie(String u, String p) {
        try {
            return new ServiceCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCourseSelectCookie(String u, String p) {
        try {
            return new CourseSelectCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLibraryCookie(String u, String p) {
        try {
            return new LibraryCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDataCenterCookie(String u, String p) {
        try {
            return new DataCenterCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLibrarySeatCookie(String u, String p) {
        try {
            return new LibrarySeatCasLogin(lockUtil).serve(u, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCookie(AbstractCasLogin casLogin, Integer id) {
        lockUtil.lockOrWait(casLogin.getSiteName() + "@" + id);

        String cookie = redisUtil.get(String.format(COOKIE_USER_KEY, casLogin.getSiteName(), id));
        if ("".equals(cookie)) {
            try {
                BasicCasUser casUser = casMapper.selectById(id);
                cookie = casLogin.serve(casUser.getStuNum(), casUser.getCasPassword());

                //cookie不包含null，表示登录成功
                if (!cookie.contains("null")) {
                    redisUtil.set(String.format(COOKIE_USER_KEY, casLogin.getSiteName(), id), cookie, COOKIE_EXPIRE_TIME);
                }
            } catch (IOException e) {
                cookie = "null";
                e.printStackTrace();
            }
        }

        lockUtil.releaseKey(casLogin.getSiteName() + "@" + id);

        return cookie;
    }
}
