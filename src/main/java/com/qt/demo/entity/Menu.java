package com.qt.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Menu)表实体类
 *
 * @author makejava
 * @since 2024-12-06 14:07:34
 */
@Data
@TableName("t_menu")
public class Menu extends Model<Menu> {

    private Integer id;

    private String name;

    private Integer parentId;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

