package com.starvel.forum.controller;


import com.starvel.common.result.Result;
import com.starvel.forum.data.po.BasicUser;
import com.starvel.forum.service.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2020-12-17 21:20
 * Description :
 */
@RequestMapping("/api/basic_user")
@RestController
public class BasicUserController {

    @Autowired
    private BasicUserService userService;

    /**
     * @api {get} /api/basic_user/info 获取个人信息
     * @apiVersion 0.0.1
     * @apiName GetUserInfo
     * @apiGroup User
     * @apiDescription 获取个人信息
     * @apiHeader {String} TOKEN 系统的用户TOKEN，通过系统登录获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "TOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiSuccess {Integer} userId 用户唯一识别Id
     * @apiSuccess {String} studentNum 学号
     * @apiSuccess {String} userName 姓名
     * @apiSuccess {Integer} userId 用户唯一识别Id
     * @apiSuccess {String} gender 性别
     * @apiSuccess {Integer} age 年龄
     * @apiSuccess {String} email 邮箱
     * @apiSuccess {String} introduction 个人简介
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "userId": 1,
     * "studentNum": "2066",
     * "userName": "sky",
     * "gender": 1,
     * "age": 17,
     * "email": "skyyemperor@qq.com",
     * "introduction": "爱好学习，对世界充满好奇"
     * }
     * }
     */
    @GetMapping("/info")
    public Result getUserInfo(@RequestParam("_uid_") Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * @api {post} /api/basic_user/info/update 更新个人信息
     * @apiVersion 0.0.1
     * @apiName UpdatePersonInfo
     * @apiGroup User
     * @apiDescription 更新个人信息
     * @apiHeader {String} TOKEN 系统的用户TOKEN，通过系统登录获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "TOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {String} introduction 个人简介
     * @apiParamExample {json} Request-Example:
     * {
     * "introduction":"nuleel"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success"
     * }
     */
    @PostMapping("/info/update")
    public Result updateUserInfo(@RequestParam("_uid_") Long userId,
                                 @RequestParam String introduction) {
        BasicUser basicUser = new BasicUser();
        basicUser.setUserId(userId);
        basicUser.setIntroduction(introduction);
        return userService.updateUserInfo(basicUser);
    }


    /**
     * @api {post} /api/basic_user/email/bind 绑定邮箱
     * @apiVersion 0.0.1
     * @apiName BindEmail
     * @apiGroup User
     * @apiDescription 绑定邮箱
     * @apiHeader {String} TOKEN 系统的用户TOKEN，通过系统登录获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "TOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {String} email 邮箱
     * @apiParamExample {json} Request-Example:
     * {
     * "email":"201900209891@email.sdu.edu.cn"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success"
     * }
     */
    @PostMapping("/email/bind")
    public Result bindEmail(@RequestParam("_uid_") Long userId,
                            @RequestParam String email) {
        return userService.bindEmail(userId, email);
    }

    @RequestMapping("/email/verify")
    public Result verifyEmail(@RequestParam String code) {
        return userService.verifyEmail(code);
    }


    /**
     * @api {post} /api/basic_user/stu_num/bind 绑定学号
     * @apiVersion 0.0.1
     * @apiName BindStuNum
     * @apiGroup User
     * @apiDescription 绑定学号
     * @apiHeader {String} TOKEN 系统的用户TOKEN，通过系统登录获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "TOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {String} stuNum 学号
     * @apiParam {String} password 统一身份认证密码
     * @apiParamExample {json} Request-Example:
     * {
     * "stuNum":"201900209891",
     * "password":"hsajd"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success"
     * }
     */
    @PostMapping("/stu_num/bind")
    public Result bindStuNum(@RequestParam("_uid_") Long userId,
                             @RequestParam String stuNum,
                             @RequestParam String password) {
        return userService.bindStuNum(userId, stuNum, password);
    }

}
