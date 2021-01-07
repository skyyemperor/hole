package com.starvel.hole.service;

import com.starvel.hole.data.po.Hole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starvel.common.result.Result;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by skyyemperor on 2020-12-22 1:04
 * Description :
 */
public interface HoleService extends IService<Hole> {

    Result postHole(Long holeUserId, Long parentId, String content, Integer type);

    Result getHoleInfo(Long holeId);

    Result getHoleList(Long holeId, Integer page, Integer count);

    Result getHoleReply(Long holeId, Integer page, Integer count);

    Result getPostedHole(Long holeUserId, Integer page, Integer count);

    Result likeHole(Long holeUserId, Long holeId);

    Result hasLikeHole(Long holeUserId, Long holeId);

    Result hateHole(Long holeUserId, Long holeId);

    Result hasHateHole(Long holeUserId, Long holeId);
}

