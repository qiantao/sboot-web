package com.qt.demo.controller;

import cn.hutool.json.JSONUtil;
import com.qt.demo.common.redis.RedisCache;
import com.qt.demo.consts.RedisConst;
import com.qt.demo.entity.Person;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@RestController
@RequestMapping("/xocean")
@Slf4j
public class XoceanController {

    private MyManager myManager;
    private RedisCache redisCache;
    public XoceanController(MyManager myManager, RedisCache redisCache){
        this.myManager = myManager;
        this.redisCache = redisCache;
    }

    @PostMapping("/post/json")
    public String postJson(@RequestBody Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }

    @GetMapping("/get")
    public String get(@RequestParam Map<String,Object> map){
        return JSONUtil.toJsonStr(map);
    }

    @PostMapping("/p")
    public String l(@RequestBody Person person){
//        person.setTime(LocalDateTime.now());
//        person.getTime().getSecond()
        return JSONUtil.toJsonStr(person);
    }
}
