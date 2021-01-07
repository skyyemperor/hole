package com.starvel.auth.service;

import com.starvel.common.result.Result;

/**
 * Created by skyyemperor on 2020-12-18 2:09
 * Description :
 */
public interface AuthService {

    Result register(String userName, String password, String email);

    Result login(String u, String p, String k);

    Result validate(String token);

    Result refresh(String refreshToken, String k);
}
