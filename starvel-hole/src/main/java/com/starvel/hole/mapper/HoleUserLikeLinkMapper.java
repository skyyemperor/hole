package com.starvel.hole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvel.hole.data.po.HoleUserLikeLink;
import org.apache.ibatis.annotations.Param;

/**
 * Created by skyyemperor on 2020-12-26 1:44
 * Description :
 */
public interface HoleUserLikeLinkMapper extends BaseMapper<HoleUserLikeLink> {

    Integer hasLikeOrHateHole(@Param("holeUserId") Long holeUserId,
                              @Param("holeId") Long holeId,
                              @Param("type") Integer type);

    int removeLink(@Param("holeUserId") Long holeUserId,
                   @Param("holeId") Long holeId,
                   @Param("type") Integer type);

    int addLink(@Param("holeUserId") Long holeUserId,
                @Param("holeId") Long holeId,
                @Param("type") Integer type);
}