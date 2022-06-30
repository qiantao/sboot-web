package com.qt.demo.controller.es;


import lombok.Data;


@Data
public class ErrorMetric {

    private static final long serialVersionUID = 853313367145316543L;

    private long total;

    private String errorType;

    private String errorKey;

    private String errorCode;

    /**
     * 错误详情，具体的内容
     */
    private String data;

    private String traceId;

    private String context;
    private String request;
    private String response;

    private boolean succeed = false;
    private String name;

    private String reportId;

    private String label;



    @Override
    public String toString() {
        return "ErrorMetric{" +
                "errorKey='" + errorKey + '\'' +
                "} " + super.toString();
    }
}
