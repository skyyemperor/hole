package com.starvel.hole.service.impl;

import com.starvel.auth.service.AuthService;
import com.starvel.common.constant.BaseValue;
import com.starvel.common.result.HoleError;
import com.starvel.common.result.Result;
import com.starvel.common.util.MailUtil;
import com.starvel.common.util.MapBuildUtil;
import com.starvel.common.util.RedisUtil;
import com.starvel.common.util.TokenUtil;
import com.starvel.forum.data.po.BasicUserIdentity;
import com.starvel.forum.mapper.BasicUserIdentityMapper;
import com.starvel.hole.mapper.HoleMapper;
import com.starvel.hole.mapper.HoleUserLikeLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starvel.hole.data.po.HoleUser;
import com.starvel.hole.mapper.HoleUserMapper;
import com.starvel.hole.service.HoleUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by skyyemperor on 2020-12-22 1:03
 * Description :
 */
@Service
public class HoleUserServiceImpl extends ServiceImpl<HoleUserMapper, HoleUser> implements HoleUserService {

    @Autowired
    private BasicUserIdentityMapper identityMapper;

    @Autowired
    private HoleUserMapper holeUserMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RedisUtil redisUtil;

    private ArrayList<String> names = new ArrayList<>(Arrays.asList(
            "Tom", "Jack", "Sky", "LiLi", "July", "西瓜", "南瓜"
    ));

    private static final String REDIS_STOKEN_KEY = "HOLE:STOKEN:%s";

    private static final String GENERATE_STOKEN_SUBJECT = "HOLE | HOLE虚拟账号生成";
    private static final String GENERATE_STOKEN_CONTENT = "欢迎使用HOLE！！\n\n您在使用本邮箱绑定HOLE账号，\\n" +
            "如非本人操作，请忽略。这是您在本系统随机生成的完全虚拟的账户，虚拟账户Token为【%s】，请保管好此令牌，它将作为您通往HOLE系统的凭证，" +
            "请务必注意：一个学生邮箱只能绑定一次，并且只能生成三次HOLE虚拟账户，TOKEN丢失不可找回。";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result generateSToken(Long userId) {
        //检验是否绑定邮箱，检验是否超过最大生成次数
        BasicUserIdentity userIdentity = identityMapper.selectById(userId);
        String email = userIdentity.getEmail();
        Integer stokenCnt = userIdentity.getStokenCnt();
        if (email == null) return Result.getResult(HoleError.EMAIL_IS_NOT_BINDING);
        if (stokenCnt >= BaseValue.MAX_GENERATE_STOKEN_CNT)
            return Result.getResult(HoleError.EXCEED_MAX_STOKEN_GENERATE_CNT);

        //生成随机HOLE账户和STOKEN
        //先将stoken设为UUID，便于之后从数据库中获取递增生成的hole_user_id
        String stoken = UUID.randomUUID().toString();
        HoleUser holeUser = new HoleUser(names.get((int) (Math.random() * names.size())), stoken);
        holeUserMapper.insert(holeUser);
        Long holeUserId = holeUserMapper.selectByStoken(stoken);

        //根据获取到的holeUserId重新生成stoken
        stoken = tokenUtil.generateToken(holeUserId.toString(), BaseValue.HOLE_SYSTEM_IDENTITY_ID)[0];

        //存入数据库
        holeUser.setHoleUserId(holeUserId);
        holeUser.setHoleToken(stoken);
        holeUserMapper.updateById(holeUser);

        //增加该邮箱生成stoken的次数
        userIdentity.setStokenCnt(++stokenCnt);
        identityMapper.updateById(userIdentity);

        //发送邮件
        mailUtil.sendHtml(GENERATE_STOKEN_SUBJECT, String.format(GENERATE_STOKEN_CONTENT, stoken), email);

        return Result.success(MapBuildUtil.builder().data("stoken", stoken).get());
    }

    @Override
    public Result validateStoken(String stoken) {
        String redisRes = redisUtil.get(String.format(REDIS_STOKEN_KEY, stoken));
        Long holeUserId;
        if (!"".equals(redisRes)) {
            holeUserId = Long.valueOf(redisRes);
        } else {
            holeUserId = holeUserMapper.selectByStoken(stoken);
            if (holeUserId != null)
                redisUtil.set(String.format(REDIS_STOKEN_KEY, stoken), holeUserId.toString());
        }

        if (holeUserId == null) {
            return Result.getResult(HoleError.HOLE_STOKEN_WRONG);
        } else {
            return Result.success(MapBuildUtil.builder()
                    .data("holeUserId", holeUserId)
                    .get());
        }
    }

}

