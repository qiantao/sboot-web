package com.qt.demo.controller.event;

import com.qt.demo.common.event.EventBeanFactory;
import com.qt.demo.common.event.EventEntity;
import com.qt.demo.common.sqlser.SqlServerConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/event/")
public class EventController {


    @GetMapping("push")
    public String query(){
        log.info("推送消息开始-----");
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTimeStamp(System.currentTimeMillis()+"");
        eventEntity.setBusiKey("qtqtqtqt");
        EventBeanFactory.getEventBus(EventBeanFactory.FirstEventName).post(eventEntity);
        EventEntity eventEntity2 = new EventEntity();
        eventEntity2.setTimeStamp(System.currentTimeMillis()+"");
        eventEntity2.setBusiKey("qtqtqtqt222");
        EventBeanFactory.getEventBus(EventBeanFactory.SecondEventName).post(eventEntity2);
        return "ok";
    }




}
