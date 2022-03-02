package com.qt.demo.common.event;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;


import javax.annotation.Resource;

@Slf4j
public abstract class EventBaseListener implements InitializingBean {



    @Subscribe(threadMode= ThreadMode.ASYNC)
    public void listener(EventEntity eventEntity){
      log.info("开始执行监听,数据={}", JSONUtil.toJsonStr(eventEntity));
    }


}
