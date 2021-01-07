package com.starvel.common.result;

import com.starvel.common.constant.BaseValue;

/**
 * Created by skyyemperor on 2020-12-24 0:29
 * Description :
 */
public enum BasicUserError implements ResultError {
    EMAIL_IS_NOT_SDU(40021, "请输入山大学生邮箱，后缀为【" + BaseValue.EMAIL_SUFFIX + "】"),
    EMAIL_BIND_CODE_IS_NOT_EXIST(40022, "邮箱绑定code不存在"),
    EMAIL_HAS_BIND(40023, "这个邮箱已经被绑定了哦"),

    ;

    private int code;

    private String message;


    private BasicUserError(int code, String message) {
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
