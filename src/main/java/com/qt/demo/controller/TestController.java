package com.qt.demo.controller;

import cn.hutool.core.convert.Convert;
import com.qt.demo.entity.Person;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@Slf4j
@Controller
public class TestController {

    private MyManager myManager;

    @Autowired
    public TestController(MyManager myManager){
        this.myManager = myManager;
    }

    @GetMapping("/test/mongodb")
    @ResponseBody
    public String testMongoDB(Model model){
        return myManager.testMongodb();
//        return "pages/index";
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "pages/index";
    }


    @PostMapping("/change_json")
    @ResponseBody
    public String changeJson(@RequestBody String str) {
        return "success";
    }


}
