package com.starvel.hole.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.starvel.auth.service.AuthService;
import com.starvel.common.constant.BaseValue;
import com.starvel.common.constant.PostConstants;
import com.starvel.common.util.RedisUtil;
import com.starvel.forum.data.po.BasicUserIdentity;
import com.starvel.forum.mapper.BasicUserIdentityMapper;
import com.starvel.hole.data.dto.HoleDto;
import com.starvel.hole.data.po.HoleUser;
import com.starvel.common.result.HoleError;
import com.starvel.common.result.Result;
import com.starvel.hole.mapper.*;
import com.starvel.common.util.MailUtil;
import com.starvel.common.util.MapBuildUtil;
import com.starvel.common.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starvel.hole.data.po.Hole;
import com.starvel.hole.service.HoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by skyyemperor on 2020-12-22 1:04
 * Description :
 */
@Service
public class HoleServiceImpl extends ServiceImpl<HoleMapper, Hole> implements HoleService {

    @Autowired
    private HoleMapper holeMapper;

    @Autowired
    private HoleUserMapper holeUserMapper;

    @Autowired
    private HoleUserLikeLinkMapper holeUserLikeLinkMapper;

    @Override
    @Transactional
    public Result postHole(Long holeUserId, Long parentId, String content, Integer type) {
        //检查parentId是否存在并获取rootId
        Long rootId = 0L;
        if (parentId != 0) {
            Hole parentHole = holeMapper.selectById(parentId);
            if (parentHole != null) {
                rootId = parentHole.getRootId() == 0 ? parentId : parentHole.getRootId();
            } else {
                return Result.getResult(HoleError.HOLE_IS_NOT_EXIST);
            }
        }

        //发布hole
        if (holeMapper.postHole(holeUserId, rootId, parentId, content, type) > 0) {
            //获取发布的hole
            Hole postedHole = holeMapper.getLatestHoleOfUser(holeUserId);
            HoleUser holeUser = holeUserMapper.getHoleUserInfo(holeUserId);
            return Result.success(MapBuildUtil.builder()
                    .data("hole", postedHole)
                    .data("holePoster", holeUser)
                    .get()
            );
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result getHoleInfo(Long holeId) {
        Hole hole = holeMapper.selectById(holeId);
        if (hole != null && hole.getStatus() != PostConstants.POST_DISABLE) {
            HoleDto holeDto = new HoleDto(hole, getHoleUserInfo(hole.getHoleUserId()));
            return Result.success(holeDto);
        } else {
            return Result.getResult(HoleError.HOLE_IS_NOT_EXIST);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result likeHole(Long holeUserId, Long holeId) {
        if (hasLikeOrHateHole(holeUserId, holeId, PostConstants.LIKE)) {
            //已经点过赞，之后删除link，减少点赞数
            holeUserLikeLinkMapper.removeLink(holeUserId, holeId, PostConstants.LIKE);
            holeMapper.subHoleLikeOrHate(holeId, PostConstants.LIKE);
        } else {
            //没有点过赞，之后添加link，增加点赞数
            holeUserLikeLinkMapper.addLink(holeUserId, holeId, PostConstants.LIKE);
            holeMapper.addHoleLikeOrHate(holeId, PostConstants.LIKE);
        }
        return Result.success();
    }

    @Override
    public Result hasLikeHole(Long holeUserId, Long holeId) {
        return Result.success(hasLikeOrHateHole(holeUserId, holeId, PostConstants.LIKE));
    }

    @Override
    public Result hateHole(Long holeUserId, Long holeId) {
        if (hasLikeOrHateHole(holeUserId, holeId, PostConstants.HATE)) {
            //已经点过踩，之后删除link，减少点踩数
            holeUserLikeLinkMapper.removeLink(holeUserId, holeId, PostConstants.HATE);
            holeMapper.subHoleLikeOrHate(holeId, PostConstants.HATE);
        } else {
            //没有点过踩，之后添加link，增加点踩数
            holeUserLikeLinkMapper.addLink(holeUserId, holeId, PostConstants.HATE);
            holeMapper.addHoleLikeOrHate(holeId, PostConstants.HATE);

            //若点踩数大于点赞数一定值，则封禁帖子
            Hole hole = holeMapper.selectById(holeId);
            if (hole.getHate() - hole.getLike() > PostConstants.MAX_DISABLE_CNT) {
                updateHoleStatus(holeId, PostConstants.POST_DISABLE);
            }
        }
        return Result.success();
    }

    @Override
    public Result hasHateHole(Long holeUserId, Long holeId) {
        return Result.success(hasLikeOrHateHole(holeUserId, holeId, PostConstants.HATE));
    }


    @Override
    public Result getHoleList(Long holeId, Integer page, Integer count) {
        List<HoleDto> holeDtoList = new ArrayList<>();
        List<Hole> holes = holeMapper.getHoleList(holeId, (page - 1) * count, count);
        for (Hole hole : holes) {
            holeDtoList.add(new HoleDto(hole, getHoleUserInfo(hole.getHoleUserId())));
        }
        return Result.success(holeDtoList);
    }

    @Override
    public Result getHoleReply(Long holeId, Integer page, Integer count) {
        List<HoleDto> holeDtoList = new ArrayList<>();
        List<Hole> holes = holeMapper.getHoleReply(holeId, (page - 1) * count, count);
        for (Hole hole : holes) {
            holeDtoList.add(new HoleDto(hole, getHoleUserInfo(hole.getHoleUserId())));
        }
        return Result.success(holeDtoList);
    }

    @Override
    public Result getPostedHole(Long holeUserId, Integer page, Integer count) {
        List<HoleDto> holeDtoList = new ArrayList<>();
        List<Hole> holes = holeMapper.getPostedHole(holeUserId, (page - 1) * count, count);
        for (Hole hole : holes) {
            holeDtoList.add(new HoleDto(hole, getHoleUserInfo(hole.getHoleUserId())));
        }
        return Result.success(holeDtoList);
    }

    private boolean hasLikeOrHateHole(Long holeUserId, Long holeId, int type) {
        return holeUserLikeLinkMapper.hasLikeOrHateHole(holeUserId, holeId, type) != null;
    }


    /**
     * 设置树洞状态
     */
    private void updateHoleStatus(Long holeId, int status) {
        Hole hole = new Hole();
        hole.setHoleId(holeId);
        hole.setStatus(status);
        holeMapper.updateById(hole);
    }

    /**
     * 获取holeUser的信息
     *
     * @param holeUserId
     * @return
     */
    private HoleUser getHoleUserInfo(Long holeUserId) {
        return holeUserMapper.getHoleUserInfo(holeUserId);
    }


}

