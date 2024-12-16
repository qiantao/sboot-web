package com.qt.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qt.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-06 14:07:34
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {

}

