package com.qt.demo.controller.rabbitmq;

import com.qt.demo.common.mq.RabbitMqProducer;
import com.qt.demo.common.mq.config.DirectRabbitConfig;
import com.qt.demo.common.mq.config.ExchangeConfig;
import com.qt.demo.common.mq.config.QueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rabbitmq/")
public class RabbitMqController {

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @GetMapping("send")
    public String send(){
        String messageId = String.valueOf(System.currentTimeMillis());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitMqProducer.sendDirectRouting(ExchangeConfig.TestDirectExchange, DirectRabbitConfig.TestDirectRouting,map.toString());
        rabbitMqProducer.sendQueue(QueueConfig.TestDirectQueue,map.toString());
        return "ok";
    }

}
