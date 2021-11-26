package com.qt.demo.common.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Configuration
public class DirectRabbitConfig {

    @Autowired
    @Qualifier(QueueConfig.TestDirectQueue)
    private Queue TestDirectQueue;
    @Autowired
    @Qualifier(ExchangeConfig.TestDirectExchange)
    private DirectExchange TestDirectExchange;

    public static final String TestDirectRouting = "TestDirectRouting";


    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue).to(TestDirectExchange).with(TestDirectRouting);
    }




}