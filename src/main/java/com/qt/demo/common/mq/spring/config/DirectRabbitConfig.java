package com.qt.demo.common.mq.spring.config;

import org.springframework.amqp.core.*;
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
//    @Autowired
//    @Qualifier(ExchangeConfig.TestDirectExchange)
//    private DirectExchange TestDirectExchange;
    @Autowired
    @Qualifier(ExchangeConfig.TestTopicExchange)
    private TopicExchange TestTopicExchange;

    public static final String TestDirectRouting = "yaoyao2";


    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
//    @Bean
//    Binding bindingDirect() {
//        return BindingBuilder.bind(TestDirectQueue).to(TestDirectExchange).with(TestDirectRouting);
//    }
    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue).to(TestTopicExchange).with(TestDirectRouting);
    }




}