package com.qt.demo.exception;

import com.qt.demo.enums.RequestStatusEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/20 11:06
 * @version: V1.0
 */
@Data
public class MyException extends RuntimeException{
    private Map<String,String> errorInfo = new HashMap<>();
    private RequestStatusEnum requestStatusEnum;

    public MyException(RequestStatusEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.requestStatusEnum = exceptionEnum;
    }

    public MyException put(String key, String value){
        this.errorInfo.put(key,value);
        return this;
    }

}
