package com.qt.demo.enums;

import lombok.Getter;

@Getter
public enum RequestStatusEnum {

    OK(0,"success","请求成功"),
    USER_NOT_EXIT(11,"user not exit","用户不存在"),
    PASSWORD_ERROR(12,"password error","密码错误"),
    SYSTEM_ERROR(12,"system error","系统错误"),
    TOKEN_NOT_FOUND(99,"token not found","token不存在"),
    ERROR(100,"error","接口异常"),
    ;

    private Integer code;
    private String value;
    private String msg;

    RequestStatusEnum(Integer code, String value , String msg) {
        this.code = code;
        this.value = value;
        this.msg = msg;
    }
}
