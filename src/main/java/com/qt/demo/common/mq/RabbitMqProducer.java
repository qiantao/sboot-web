package com.qt.demo.common.mq;

import com.qt.demo.common.mq.config.DirectRabbitConfig;
import com.qt.demo.common.mq.config.ExchangeConfig;
import com.qt.demo.common.mq.config.QueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
