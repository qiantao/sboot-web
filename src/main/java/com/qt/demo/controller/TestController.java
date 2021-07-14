package com.qt.demo.controller;

import com.qt.demo.entity.Person;
import com.qt.demo.manager.MyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
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

}
