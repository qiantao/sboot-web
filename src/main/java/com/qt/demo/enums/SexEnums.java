package com.qt.demo.enums;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 16:03
 * @version: V1.0
 */
public enum SexEnums {
    MAN("man","男"),
    HUMEN("humen","女"),
    ;
    private String value;
    private String name;
    SexEnums(String value,String name) {
        this.value = value;
        this.name = name;
    }
}
