package com.qt.demo.enums;

public enum TimeType {


    EVENT("EVENT", 1, "事件"),
    GRID("GRID", 2, "网格"),
    STATISTICAL("STATISTICAL", 3, "统计"),
    TEMPERATURE_CONTROL("TEMPERATURE_CONTROL", 4, "温控"),

    ;

    private String type;
    private Integer code;
    private String desc;

    TimeType(String type, Integer code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }
}
