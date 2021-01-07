package com.starvel.hole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starvel.common.result.Result;
import com.starvel.hole.data.po.Hole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by skyyemperor on 2020-12-24 0:11
 * Description :
 */
public interface HoleMapper extends BaseMapper<Hole> {

    int postHole(@Param("holeUserId") Long holeUserId,
                 @Param("rootId") Long rootId,
                 @Param("parentId") Long parentId,
                 @Param("content") String content,
                 @Param("type") Integer type);

    Hole getLatestHoleOfUser(@Param("holeUserId") Long holeUserId);

    int addHoleLikeOrHate(@Param("holeId") Long holeId, @Param("type") int type);

    int subHoleLikeOrHate(@Param("holeId") Long holeId, @Param("type") int type);

    List<Hole> getHoleList(@Param("rootId") Long rootId,
                           @Param("start") Integer start,
                           @Param("count") Integer count);

    List<Hole> getHoleReply(@Param("parentId") Long parentId,
                            @Param("start") Integer start,
                            @Param("count") Integer count);

    List<Hole> getPostedHole(@Param("holeUserId") Long holeUserId,
                             @Param("start") Integer start,
                             @Param("count") Integer count);
}