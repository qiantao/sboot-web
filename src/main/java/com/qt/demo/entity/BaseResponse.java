package com.qt.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qt.demo.enums.RequestStatusEnum;
import lombok.Data;

import java.util.Collections;

@Data
public class BaseResponse<T> {
    @JsonProperty("code")
    private String code = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("data")
    private T data = null;

    public BaseResponse code(String code) {
        this.code = code;
        return this;
    }

    public static <T> BaseResponse<T> result(RequestStatusEnum requestStatusEnum, T data) {
        if (requestStatusEnum == null) {
            return result();
        }
        BaseResponse<T> response = new BaseResponse<>();
        response.code = requestStatusEnum.getCode().toString();
        response.message = requestStatusEnum.getMsg();
        response.data = data;
        return response;
    }

    public static <T> BaseResponse<T> result(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = RequestStatusEnum.OK.getCode().toString();
        response.message = RequestStatusEnum.OK.getMsg();
        response.data = data;
        return response;
    }
    public static <T> BaseResponse<T> result(RequestStatusEnum requestStatusEnum) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = requestStatusEnum.getCode().toString();
        response.message = requestStatusEnum.getMsg();
        response.data = (T) Collections.emptyMap();
        return response;
    }

    public static <T> BaseResponse<T> result() {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = RequestStatusEnum.OK.getCode().toString();
        response.message = RequestStatusEnum.OK.getMsg();
        response.data = (T) Collections.emptyMap();
        return response;
    }

}
