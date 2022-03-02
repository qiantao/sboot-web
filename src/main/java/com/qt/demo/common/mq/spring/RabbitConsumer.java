package com.qt.demo.common.mq.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j

public class RabbitConsumer {

//    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
//    public void process(String testMessage) {
//        System.out.println("TestDirectQueue 消费者收到消息  : " + testMessage);
//    }
}
