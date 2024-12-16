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
public class DataFilterConfig {

    private String systemCode;

    private List<DataPropertyFilterConfig> propertyFilterConfigList;
}
