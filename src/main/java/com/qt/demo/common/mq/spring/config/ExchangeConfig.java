package com.qt.demo.common.mq.spring.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

//    public static final String TestDirectExchange ="testdirect";
    public static final String TestTopicExchange ="testtopic";
    public static final String lonelyDirectExchange ="lonelyDirectExchange";
//    @Bean
//    @Qualifier(TestDirectExchange)
//    DirectExchange TestDirectExchange() {
//        return new DirectExchange(TestDirectExchange,true,false);
//    }
    @Bean
    @Qualifier(TestTopicExchange)
    TopicExchange TestTopicExchange() {
        return new TopicExchange(TestTopicExchange,true,false);
    }

    @Bean
    @Qualifier(lonelyDirectExchange)
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange(lonelyDirectExchange);
    }

}
