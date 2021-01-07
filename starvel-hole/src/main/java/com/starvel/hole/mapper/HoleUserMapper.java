package com.starvel.hole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvel.hole.data.po.HoleUser;
import org.apache.ibatis.annotations.Param;

/**
 * Created by skyyemperor on 2020-12-24 0:13
 * Description :
 */
public interface HoleUserMapper extends BaseMapper<HoleUser> {
    Long selectByStoken(String stoken);

    HoleUser getHoleUserInfo(@Param("holeUserId") Long holeUserId);
}