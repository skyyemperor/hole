package com.starvel.forum.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvel.forum.data.po.BasicUserIdentity;
import org.apache.ibatis.annotations.Param;

/**
 * Created by skyyemperor on 2020-12-25 2:13
 * Description :
 */
public interface BasicUserIdentityMapper extends BaseMapper<BasicUserIdentity> {
    BasicUserIdentity selectByUserNameOrEmail(@Param("u") String u);
}