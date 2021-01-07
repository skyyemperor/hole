package com.starvel.hole.service;

import com.starvel.common.result.Result;
import com.starvel.hole.data.po.HoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created by skyyemperor on 2020-12-22 1:03
 * Description :
 */
public interface HoleUserService extends IService<HoleUser> {

    Result generateSToken(Long userId);

    Result validateStoken(String stoken);
}

