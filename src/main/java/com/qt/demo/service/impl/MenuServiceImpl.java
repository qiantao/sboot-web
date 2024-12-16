package com.qt.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qt.demo.dao.MenuDao;
import com.qt.demo.entity.Menu;
import com.qt.demo.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-12-06 14:07:34
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

}

