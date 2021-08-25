package com.qt.demo.exception;

import com.qt.demo.enums.TscStatusEnum;
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
public class TscException extends Exception{
    private Map<String,String> errorInfo = new HashMap<>();
    private TscStatusEnum tscStatusEnum;

    public TscException(TscStatusEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.tscStatusEnum = exceptionEnum;
    }

    public TscException put(String key,String value){
        this.errorInfo.put(key,value);
        return this;
    }

}
