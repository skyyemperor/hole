package com.starvel.auth.service.impl;

import com.starvel.auth.data.po.SecurityUserRole;
import com.starvel.auth.mapper.SecurityUserRoleMapper;
import com.starvel.auth.service.AuthService;
import com.starvel.common.constant.BaseValue;
import com.starvel.common.constant.UserRoleEnum;
import com.starvel.common.result.AuthError;
import com.starvel.common.result.BasicUserError;
import com.starvel.common.result.CommonError;
import com.starvel.common.result.Result;
import com.starvel.common.util.BaseUtil;
import com.starvel.common.util.MapBuildUtil;
import com.starvel.common.util.TaskExecutorUtil;
import com.starvel.common.util.TokenUtil;
import com.starvel.forum.data.po.BasicUser;
import com.starvel.forum.data.po.BasicUserIdentity;
import com.starvel.forum.data.po.BasicUserStatus;
import com.starvel.forum.mapper.BasicUserIdentityMapper;
import com.starvel.forum.mapper.BasicUserMapper;
import com.starvel.forum.mapper.BasicUserStatusMapper;
import com.starvel.forum.service.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by skyyemperor on 2020-12-18 2:11
 * Description :
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private BasicUserMapper basicUserMapper;

    @Autowired
    private BasicUserIdentityMapper identityMapper;

    @Autowired
    private SecurityUserRoleMapper securityUserRoleMapper;

    @Autowired
    private BasicUserService basicUserService;

    @Autowired
    private BasicUserStatusMapper userStatusMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private TaskExecutorUtil<?> taskExecutorUtil;

    @Override
    public Result login(String u, String p, String k) {
        BasicUserIdentity basicUser = identityMapper.selectByUserNameOrEmail(u);
        if (basicUser == null || !p.equals(basicUser.getPassword())) {
            return Result.getResult(AuthError.LOGIN_FAIL);
        }

        String[] token = tokenUtil.generateToken(basicUser.getBasicUserId().toString(), k);

        //更新最后一次登录时间
        userStatusMapper.updateById(new BasicUserStatus(basicUser.getBasicUserId(),new Date()));

        return Result.success(MapBuildUtil.builder()
                .data("token", token[0])
                .data("refresh_token", token[1])
                .get()
        );
    }

    @Override
    public Result validate(String token) {
        String basicUserId = tokenUtil.validate(token);
        if (BaseUtil.NOTNULL(basicUserId)) {
            return Result.success(MapBuildUtil.builder().data("_uid_", basicUserId).get());
        } else {
            return Result.getResult(CommonError.LOGIN_STATUS_IS_INVALID);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Result register(String userName, String password, String email) {
        //检验参数合法性
        if (userName.length() < 2 || userName.length() > 16)
            return Result.getResult(AuthError.NICKNAME_LENGTH_WRONG);
        if (!judgeUserName(userName))
            return Result.getResult(AuthError.NICKNAME_CHARACTER_WRONG);
        if (password.length() < 6 || password.length() > 16)
            return Result.getResult(AuthError.PASSWORD_LENGTH_WRONG);

        //查看该用户名是否已被注册
        BasicUser basicUser = basicUserMapper.selectByUserName(userName);
        if (basicUser != null) {
            return Result.getResult(AuthError.USERNAME_HAS_EXIST);
        }

        if (email != null) {
            //检验邮箱合法性
            if (!email.endsWith(BaseValue.EMAIL_SUFFIX))
                return Result.getResult(BasicUserError.EMAIL_IS_NOT_SDU);

            //判断该邮箱是否已绑定其他账户
            if (identityMapper.selectByUserNameOrEmail(email) != null)
                return Result.getResult(BasicUserError.EMAIL_HAS_BIND);
        }

        //注册
        Long insertedUserId = -1L;
        if (basicUserMapper.insert(new BasicUser(userName)) > 0) {
            insertedUserId = basicUserMapper.selectByUserName(userName).getUserId();
            identityMapper.insert(new BasicUserIdentity(insertedUserId, userName, password));
            userStatusMapper.insert(new BasicUserStatus(insertedUserId, new Date(), 0));
            securityUserRoleMapper.insert(new SecurityUserRole(insertedUserId, UserRoleEnum.ORGIN_USER.name()));
        }

        //绑定邮箱
        Long newThreadInsertedUserId = insertedUserId;
        taskExecutorUtil.run(() -> {
            if (email != null && newThreadInsertedUserId != -1L) {
                basicUserService.bindEmail(newThreadInsertedUserId, email);
            }
        });

        return login(userName, password, "");
    }


    @Override
    public Result refresh(String refreshToken, String k) {
        String[] result = tokenUtil.refresh(refreshToken, k);
        if (result.length == 0) {
            return Result.getResult(CommonError.LOGIN_STATUS_IS_INVALID);
        } else {
            return Result.success(MapBuildUtil.builder()
                    .data("token", result[0])
                    .data("refresh_token", result[1])
                    .get()
            );
        }
    }

    private boolean judgeUserName(String userName) {
        for (char c : userName.toCharArray()) {
            if (!((c >= 0x4E00 && c <= 0x9FA5) //不是汉字
                    || (c >= 0x0030 && c <= 0x0039) //不是数字
                    || (c >= 0x0041 && c <= 0x005A) //不是英文
                    || (c >= 0x0061 && c <= 0x007A) //不是英文
                    || (c == '-' || c == '_'))) {
                return false;
            }
        }
        return true;
    }


}
