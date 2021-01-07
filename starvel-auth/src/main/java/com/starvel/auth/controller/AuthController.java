package com.starvel.auth.controller;

import com.starvel.common.result.Result;
import com.starvel.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2020-12-17 21:20
 * Description :
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * @api {post} /api/auth/register 注册
     * @apiVersion 0.0.1
     * @apiName UserRegister
     * @apiGroup Auth
     * @apiDescription 用户注册
     * @apiParam {String} userName 用户名
     * @apiParam {String} password 密码
     * @apiParam {String} email 邮箱,后缀为`@mail.sdu.edu.cn`
     * @apiParamExample {json} Request-Example:
     * {
     * "userName": "这是一个用户名",
     * "password": "password123",
     * "email": "20103812442@mail.sdu.edu.cn"
     * }
     * @apiSuccess {String} token Token
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "refresh_token": "8E4B37950453BF4538DAC7E94163FB091-581869",
     * "token": "00D3CBBCA8ECA5C8E35B03AB5356C9E53-576520"
     * }
     * }
     * @apiError 40012 用户名长度应为2~16位
     * @apiError 40013 用户名不可包含除-和_以外的特殊字符
     * @apiError 40014 密码长度应为6~16位
     */
    @PostMapping("/register")
    public Result register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam(required = false) String email
    ) {
        return authService.register(userName, password, email);
    }

    /**
     * @api {post} /api/auth/login 登录
     * @apiVersion 0.0.1
     * @apiName UserLogin
     * @apiGroup Auth
     * @apiDescription 用户登录
     * @apiParam {String} u 用户名或邮箱
     * @apiParam {String} p 密码
     * @apiParam {String} [k=""] 唯一应用标识
     * @apiParamExample {json} Request-Example:
     * {
     * "u": "sky",
     * "p": "password123"
     * }
     * @apiSuccess {String} token Token
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "refresh_token": "8E4B37950453BF4538DAC7E94163FB091-581869",
     * "token": "00D3CBBCA8ECA5C8E35B03AB5356C9E53-576520"
     * }
     * }
     * @apiError 40011 你的帐号或密码输错了哦
     */
    @PostMapping("/login")
    public Result login(@RequestParam(value = "u") String userName,
                        @RequestParam(value = "p") String password,
                        @RequestParam(required = false) String k
    ) {
        return authService.login(userName, password, k);
    }


    /**
     * @api {post} /api/auth/refresh 刷新token
     * @apiVersion 0.0.1
     * @apiName TokenRefresh
     * @apiGroup Auth
     * @apiDescription 刷新token
     * @apiParam {String} refresh_token refresh_token
     * @apiParam {String} [k=""] 唯一应用标识
     * @apiParamExample {json} Request-Example:
     * {
     * "refresh_token": "13846B0F4DFD9469C7EFCE9B0F8CA9E3"
     * }
     * @apiSuccess {String} token Token
     * @apiSuccess {String} refresh_token refresh_token
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "refresh_token": "8E4B37904553B4538DAC7E94163FB091-581869",
     * "token": "00D3CBBCA8ECAC8E355B3AB5356C9E53-576520"
     * }
     * }
     * @apiError 40001 登录状态已失效，请重新登录
     */
    @PostMapping("/refresh")
    public Result refresh(@RequestParam("refresh_token") String refreshToken,
                          @RequestParam(required = false) String k) {
        return authService.refresh(refreshToken, k);
    }

    @PostMapping("/validate")
    public Result validate(@RequestParam String token) {
        return authService.validate(token);
    }


}
