package com.qt.demo.entity;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/20 14:59
 * @version: V1.0
 */
@Data
public class ExceptionResponse {
    private String msg;
    private Map<String,String> errorInfo ;
}
