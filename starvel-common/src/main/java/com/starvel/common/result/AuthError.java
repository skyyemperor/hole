package com.starvel.common.result;

/**
 * Created by skyyemperor on 2020-12-18 2:05
 * Description :
 */
public enum AuthError implements ResultError {
    LOGIN_FAIL(40011, "你的帐号或密码输错了哦"),
    NICKNAME_LENGTH_WRONG(40012, "用户名长度应为2~16位"),
    NICKNAME_CHARACTER_WRONG(40013, "用户名不可包含除-和_以外的特殊字符"),
    PASSWORD_LENGTH_WRONG(40014, "密码长度应为6~16位"),
    USERNAME_HAS_EXIST(40015, "用户名已存在"),

    ;

    private int code;

    private String message;


    private AuthError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
