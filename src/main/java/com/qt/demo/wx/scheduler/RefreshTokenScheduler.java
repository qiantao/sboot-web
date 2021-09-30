package com.qt.demo.wx.scheduler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qt.demo.consts.RedisConst;
import com.qt.demo.util.HttpUtil;
import com.qt.demo.wx.conts.WxUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/06 16:06
 * @version: V1.0
 */
@Component
@Slf4j
public class RefreshTokenScheduler {
    private final StringRedisTemplate redisTemplate;
    //wxd2f22a54c9add0eb
    private static final String appid = "wxd2f22a54c9add0eb";
    //eaf11793bb1ddf38d550ff54655a2459
    private static final String key = "eaf11793bb1ddf38d550ff54655a2459";

    public RefreshTokenScheduler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    @Scheduled(fixedDelay = 1000 * 6 )
    public void c(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("测试线程={}",sdf.format(new Date()));
    }
//    @Scheduled(fixedDelay = 1000 * 60 * 60 )
    public void logScheduler(){

        String url = String.format(WxUrl.GET_TOKEN,appid,key);
        String s = HttpUtil.doGetJson(url);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        String accessToken = jsonObject.getStr("access_token");
        Long expireIn = jsonObject.getLong("expires_in");
        if(StrUtil.isEmpty(accessToken)){
            log.error("get wx token error >>>>error msg = {}",s);
            return;
        }
        redisTemplate.opsForValue().set(RedisConst.WX_TOKEN,accessToken,expireIn, TimeUnit.SECONDS);
    }

}
