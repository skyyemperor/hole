package com.starvel.common.result;

/**
 * Created by skyyemperor on 2020-12-25 2:29
 * Description :
 */
public enum HoleError implements ResultError {
    EMAIL_IS_NOT_BINDING(40031, "啊哦，要先绑定邮箱才能生成HOLE账户哦"),
    EXCEED_MAX_STOKEN_GENERATE_CNT(40032, "啊哦，当前邮箱最多只能生成三次哦"),
    HOLE_IS_NOT_EXIST(40033, "啊哦，这个树洞不存在了"),
    HOLE_STOKEN_WRONG(40034, "该树洞Token不存在或已失效");


    private int code;

    private String message;


    private HoleError(int code, String message) {
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
