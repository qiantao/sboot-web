package com.qt.demo.controller;

import com.qt.demo.common.redis.RedisCache;
import com.qt.demo.consts.RedisConst;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
    private RedisCache redisCache;
    public TestController(MyManager myManager, RedisCache redisCache){
        this.myManager = myManager;
        this.redisCache = redisCache;
    }

    @GetMapping("/test/mongodb")
    @ResponseBody
    public String testMongoDB(Model model){
//        return myManager.testMongodb();
        System.out.println("s");
        System.out.println("s");
        System.out.println("s");
        System.out.println("s");
        System.out.println("s");
        System.out.println("s");
        return redisCache.getKeys(String.format(RedisConst.USER_GROUP, "*")).toString();
//        return "pages/index";
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "pages/index";
    }

    @RequestMapping("/photo")
    public String photo(Model model) {
        return "pages/photo";
    }

    @RequestMapping("/*.html")
    public String order(Model model) {
//        Request
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        requestURI.lastIndexOf("/");
        String substring = requestURI.substring(requestURI.lastIndexOf("/")+1, requestURI.lastIndexOf("."));
        System.out.println(substring);
        return "pages/"+substring;
    }


    @PostMapping("/change_json")
    @ResponseBody
    public String changeJson(@RequestBody String str) {

        return "success";
    }




}
