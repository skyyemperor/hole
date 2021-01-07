package com.starvel.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvel.forum.data.po.BasicUser;

/**
 * Created by skyyemperor on 2020-12-22 1:23
 * Description :
 */
public interface BasicUserMapper extends BaseMapper<BasicUser> {

    BasicUser selectByUserName(String userName);
}