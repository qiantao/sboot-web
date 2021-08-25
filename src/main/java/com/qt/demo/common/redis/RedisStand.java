package com.qt.demo.common.redis;

import com.qt.demo.consts.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/25 14:36
 * @version: V1.0
 */
@Component
public class RedisStand implements RedisCache{

    private final StringRedisTemplate redisTemplate;

    public RedisStand(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean set(String key, String value, Long seconds) {
        redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Boolean remove(String key) {

        return redisTemplate.delete(key);
    }

    @Override
    public Set<String> getKeys(String pattern){
        return redisTemplate.keys(pattern);
    }
}
