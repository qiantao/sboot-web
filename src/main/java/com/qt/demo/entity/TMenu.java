package com.qt.demo.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TMenu)实体类
 *
 * @author makejava
 * @since 2024-12-06 14:04:28
 */
public class TMenu implements Serializable {
    private static final long serialVersionUID = -59148492309327122L;

    private Integer id;

    private String name;

    private Integer parentId;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

}

