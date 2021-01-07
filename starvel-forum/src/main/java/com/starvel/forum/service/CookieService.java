package com.starvel.forum.service;

/**
 * Created by skyyemperor on 2020-12-25 1:18
 * Description :
 */
public interface CookieService {

    String getEduCookie(String u, String p);

    String getServiceCookie(String u, String p);

    String getCourseSelectCookie(String u, String p);

    String getLibraryCookie(String u, String p);

    String getDataCenterCookie(String u, String p);

    String getLibrarySeatCookie(String u, String p);
}
