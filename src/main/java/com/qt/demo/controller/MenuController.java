package com.qt.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qt.demo.entity.Menu;
import com.qt.demo.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2024-12-06 14:07:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController  {
    /**
     * 服务对象
     */
    private final MenuService menuService;

    /**
     * 分页查询所有数据
     *
     * @param menu 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Page<Menu> selectAll(Menu menu) {
        return menuService.page(new Page<Menu>(1L, 10L), new QueryWrapper<>(menu));
    }


    /**
     * 新增数据
     *
     * @param menu 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Boolean insert(@RequestBody Menu menu) {
        return this.menuService.save(menu);
    }

    /**
     * 修改数据
     *
     * @param menu 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Boolean update(@RequestBody Menu menu) {
        return this.menuService.updateById(menu);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Boolean delete(@RequestParam("idList") List<Long> idList) {
        return this.menuService.removeByIds(idList);
    }
}

