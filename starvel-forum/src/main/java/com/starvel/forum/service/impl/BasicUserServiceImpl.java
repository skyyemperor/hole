package com.starvel.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starvel.common.constant.BaseValue;
import com.starvel.common.result.AuthError;
import com.starvel.common.result.BasicUserError;
import com.starvel.common.result.Result;
import com.starvel.common.util.BaseUtil;
import com.starvel.common.util.MailUtil;
import com.starvel.common.util.RedisUtil;
import com.starvel.common.util.TaskExecutorUtil;
import com.starvel.forum.cas.AbstractCasLogin;
import com.starvel.forum.cas.sites.LibraryCasLogin;
import com.starvel.forum.data.po.BasicCasUser;
import com.starvel.forum.data.po.BasicUser;
import com.starvel.forum.data.po.BasicUserIdentity;
import com.starvel.forum.mapper.BasicCasUserMapper;
import com.starvel.forum.mapper.BasicUserIdentityMapper;
import com.starvel.forum.mapper.BasicUserMapper;
import com.starvel.forum.service.BasicUserService;
import com.starvel.forum.util.AcademicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;


/**
 * Created by skyyemperor on 2020-12-22 1:23
 * Description :
 */
@Service
public class BasicUserServiceImpl extends ServiceImpl<BasicUserMapper, BasicUser> implements BasicUserService {

    @Autowired
    private BasicUserMapper basicUserMapper;

    @Autowired
    private BasicCasUserMapper casMapper;

    @Autowired
    private BasicUserIdentityMapper identityMapper;

    @Autowired
    private TaskExecutorUtil<?> taskExecutorUtil;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AcademicUtil academicUtil;

    private static final String REDIS_BIND_EMAIL = "HOLE:BIND-EMAIL:CODE:%s";
    private static final Long REDIS_BIND_EMAIL_TIME = 30 * 60 * 1000L; //半小时

    private static final String BIND_EMAIL_VERIFY_URL = BaseValue.HOST + "/api/basic_user/email/verify?code=";
    private static final String BIND_EMAIL_SUBJECT = "HOLE | 邮箱绑定验证";
    private static final String BIND_EMAIL_CONTENT = "<h4>欢迎使用HOLE！！</h4><br>您在使用本邮箱绑定HOLE账号，如非本人操作，请忽略。\n" +
            "<br>如链接点击无反应，请复制下方链接至浏览器中打开，此链接半小时内有效。<br><a href=\"%s\">%s</a>";
    private static final String BIND_EMAIL_SUCCESS_PAGE = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>邮箱绑定成功</title></head><body>\n" +
            "<h2>邮箱绑定成功！！</h2 align=\"center\"><a href=\"http://skyemperor.top/front_project/hole/\">点我跳转主页面</a></body></html>";

    @Override
    public Result getUserInfo(Long userId) {
        return Result.success(basicUserMapper.selectById(userId));
    }

    @Override
    public Result updateUserInfo(BasicUser basicUser) {
        if (basicUserMapper.updateById(basicUser) > 0) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result bindEmail(Long userId, String email) {
        //检验邮箱合法性
        if (!email.endsWith(BaseValue.EMAIL_SUFFIX)) {
            return Result.getResult(BasicUserError.EMAIL_IS_NOT_SDU);
        }

        //判断该邮箱是否已绑定其他账户
        if (identityMapper.selectByUserNameOrEmail(email) != null) {
            return Result.getResult(BasicUserError.EMAIL_HAS_BIND);
        }

        String verifyLink = generateEmailBindLink(userId, email);
        mailUtil.sendHtml(BIND_EMAIL_SUBJECT, String.format(BIND_EMAIL_CONTENT, verifyLink, verifyLink), email);

        return Result.success();
    }

    @Override
    public Result verifyEmail(String code) {
        String[] userIdAndEmail = redisUtil.get(String.format(REDIS_BIND_EMAIL, code)).split("\\|");
        if (userIdAndEmail.length != 2) {
            return Result.getResult(BasicUserError.EMAIL_BIND_CODE_IS_NOT_EXIST);
        }

        BasicUserIdentity userIdentity = new BasicUserIdentity();
        userIdentity.setBasicUserId(Long.valueOf(userIdAndEmail[0]));
        userIdentity.setEmail(userIdAndEmail[1]);

        //判断该邮箱是否已绑定其他账户
        if (identityMapper.selectByUserNameOrEmail(userIdAndEmail[1]) != null) {
            return Result.getResult(BasicUserError.EMAIL_HAS_BIND);
        }

        if (identityMapper.updateById(userIdentity) > 0) {
            return Result.success(BIND_EMAIL_SUCCESS_PAGE);
        } else {
            return Result.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result bindStuNum(Long userId, String u, String p) {
        if (stuNumLogin(userId, u, p)) {
            return Result.success();
        } else {
            return Result.getResult(AuthError.LOGIN_FAIL);
        }
    }

    private boolean stuNumLogin(Long userId, String u, String p) {
        BasicCasUser casUser = casMapper.selectById(userId);

        if (casUser != null && !casUser.getCasPassword().equals(p) && !directLogin(u, p)) {
            return false;
        } else {
            if (casUser == null) {
                casMapper.insert(new BasicCasUser(userId, u, p));
            } else {
                casMapper.updateById(new BasicCasUser(userId, u, p));
            }

            //异步更新用户信息
            updateUserInfoSync(userId, u, p);
            return true;
        }
    }


    /**
     * @param u 学工号
     * @param p 统一认证密码
     * @return 是否登录成功
     */
    private boolean directLogin(String u, String p) {
        AbstractCasLogin casLogin = new LibraryCasLogin();
        try {
            return !casLogin.serve(u, p).contains("null");
        } catch (IOException e) {
            return false;
        }
    }


    private void updateUserInfoSync(Long userId, String u, String p) {
        taskExecutorUtil.run(() -> {
            //更新个人信息
            BasicUser basicUser = academicUtil.getUserInformation(userId, u, p);
            basicUserMapper.updateById(basicUser);
        });
    }

    private String generateEmailBindLink(Long userId, String email) {
        String emailBindCode = BaseUtil.encodeByMd5("HOLE", UUID.randomUUID(), userId, email);
        redisUtil.set(String.format(REDIS_BIND_EMAIL, emailBindCode), userId + "|" + email, REDIS_BIND_EMAIL_TIME);
        return BIND_EMAIL_VERIFY_URL + emailBindCode;
    }

}
