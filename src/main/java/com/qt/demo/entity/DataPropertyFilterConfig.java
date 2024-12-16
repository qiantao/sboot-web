package com.qt.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/07/11 上午11:21
 * @version: V1.0
 */
@Data
public class DataPropertyFilterConfig {

    private String columnName;

    private List<String> columnValue;

    private String defaultValue;

    private boolean isRemove;
}
