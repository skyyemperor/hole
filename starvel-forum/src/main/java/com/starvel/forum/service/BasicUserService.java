package com.starvel.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starvel.common.result.Result;
import com.starvel.forum.data.po.BasicUser;

/**
 * Created by skyyemperor on 2020-12-22 1:23
 * Description :
 */
public interface BasicUserService extends IService<BasicUser> {

    Result getUserInfo(Long userId);

    Result updateUserInfo(BasicUser basicUser);

    Result bindEmail(Long userId, String email);

    Result verifyEmail(String code);

    Result bindStuNum(Long userId, String u, String p);
}
