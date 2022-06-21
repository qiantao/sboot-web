package com.qt.demo.entity;

import lombok.Data;

/**
 * @Description
 * @Author MuYang @Perfma
 * @Date 2022/03/10 17:37
 * @version: V1.0
 */
@Data
public class BasePageQueryVO {
    /**
     * 页码
     */
    private Integer current;

    /**
     * 每页大小（-1 查询所有）
     *
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderBy;
}
