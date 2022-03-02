package com.qt.demo.controller.redis;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/09/07 09:41
 * @version: V1.0
 */
@RestController
@RequestMapping("redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String DEFAULT_KEY = "kkkkkkkkkk_";
    @GetMapping("setAdd")
    @ApiOperation(value = "redis测试", notes = "redis测试")
    public String setAdd(@RequestParam("key") String key){
        if(StrUtil.isEmpty(key)){
            key = DEFAULT_KEY;
        }

        Long add = redisTemplate.opsForSet().add(DEFAULT_KEY, "1", "2", "3", "3", "4");

        return add.toString();
    }
    @GetMapping("setGetAll")
    @ApiOperation(value = "redis测试", notes = "redis测试")
    public String setGetAll(@RequestParam("key") String key){
        if(StrUtil.isEmpty(key)){
            key = DEFAULT_KEY;
        }
        Set<String> members = redisTemplate.opsForSet().members(key);
        return members.toString();
    }

    @GetMapping("con")
    public String setGetAll(){
        Config config = new Config();
        config.useSingleServer();

        Redisson.create();
        return "";
    }
}
