package com.qt.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qt.demo.enums.TscStatusEnum;
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

    public static <T> BaseResponse<T> result(TscStatusEnum tscStatusEnum, T data) {
        if (tscStatusEnum == null) {
            return result();
        }
        BaseResponse<T> response = new BaseResponse<>();
        response.code = tscStatusEnum.getCode().toString();
        response.message = tscStatusEnum.getMsg();
        response.data = data;
        return response;
    }

    public static <T> BaseResponse<T> result(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = TscStatusEnum.OK.getCode().toString();
        response.message = TscStatusEnum.OK.getMsg();
        response.data = data;
        return response;
    }
    public static <T> BaseResponse<T> result(TscStatusEnum tscStatusEnum) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = tscStatusEnum.getCode().toString();
        response.message = tscStatusEnum.getMsg();
        response.data = (T) Collections.emptyMap();
        return response;
    }

    public static <T> BaseResponse<T> result() {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = TscStatusEnum.OK.getCode().toString();
        response.message = TscStatusEnum.OK.getMsg();
        response.data = (T) Collections.emptyMap();
        return response;
    }

}
