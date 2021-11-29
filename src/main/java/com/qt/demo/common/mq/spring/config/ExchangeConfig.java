package com.qt.demo.common.mq.spring.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    public static final String TestDirectExchange ="TestDirectExchange";
    public static final String lonelyDirectExchange ="lonelyDirectExchange";
    @Bean
    @Qualifier(TestDirectExchange)
    DirectExchange TestDirectExchange() {
        return new DirectExchange(TestDirectExchange,true,false);
    }

    @Bean
    @Qualifier(lonelyDirectExchange)
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange(lonelyDirectExchange);
    }
}
