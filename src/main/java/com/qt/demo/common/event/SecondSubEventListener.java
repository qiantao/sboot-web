package com.qt.demo.common.event;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecondSubEventListener extends EventBaseListener{


    @Override
//    @Subscribe(threadMode= ThreadMode.MAIN)
    public void listener(EventEntity eventEntity) {
        log.info("SecondSubEventListener 开始监听 : {}", JSONUtil.toJsonStr(eventEntity));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventBeanFactory.getEventBus(EventBeanFactory.SecondEventName).register(this);
    }
}
