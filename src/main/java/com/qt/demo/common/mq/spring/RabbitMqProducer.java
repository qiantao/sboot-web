package com.qt.demo.common.mq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendQueue(String queue,String message){
        rabbitTemplate.convertAndSend(queue,message);
    }

    public void sendDirectRouting(String exchange,String routingKey,String message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }

}
