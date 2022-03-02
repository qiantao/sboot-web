package com.qt.demo.config;

import org.greenrobot.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("event1")
    public EventBus eventBus1(){
        return new EventBus();
    }

    @Bean("event2")
    public EventBus eventBus2(){
        return new EventBus();
    }
}
