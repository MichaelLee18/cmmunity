package com.wd.enums;

import com.wd.exception.ICustomizeError;

public enum CustomizeErrorStatus implements ICustomizeError {
    QUESTION_NOT_FOUND(2000, "你找的问题已经不在了,请换个试试!!!"),
    TARGET_COMMENT_NOT_FOUND(2001, "没有选择任何评论,请选择评论进行回复!!!"),
    USER_NOT_LOGIN(2003,"用户换没有登录，请登录！！！"),
    SYSTEM_ERROR(2004,"用户换没有登录，请登录！！！"),
    COMMENT_TYPE_ERROR(2005,"评论或回复类型错误！！！"),
    COMMENT_NOT_FOUND(2006,"你回复的评论已经不在了,请换个试试!!!")
    ;


    private final String message;
    private final int code;

    CustomizeErrorStatus(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
