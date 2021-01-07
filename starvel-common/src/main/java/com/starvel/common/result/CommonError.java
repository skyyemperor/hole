package com.starvel.common.result;

public enum CommonError implements ResultError {
    LOGIN_STATUS_IS_INVALID(40001, "登录状态已失效，请重新登录"),
    PERM_NOT_ALLOW(40002, "权限不足"),
    NETWORK_WRONG(40003, "网络错误"),
    PARAM_FORMAT_WRONG(40004, "参数范围或格式错误"),
    ;


    private int code;

    private String message;


    private CommonError(int code, String message) {
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
