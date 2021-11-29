package com.qt.demo.controller.rabbitmq;

import com.qt.demo.common.mq.java.MQHttp;
import com.qt.demo.common.mq.java.MQSendUtil;
import com.qt.demo.common.mq.spring.RabbitMqProducer;
import com.qt.demo.common.mq.spring.config.DirectRabbitConfig;
import com.qt.demo.common.mq.spring.config.ExchangeConfig;
import com.qt.demo.common.mq.spring.config.QueueConfig;
import com.qt.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @GetMapping("send1")
    public String send1(){
        try {
            String queueName = "qtqueue";
            String msg = "hallo word";
//            MQSendUtil.send(queueName,msg);
            MQSendUtil.bind(queueName);
            String url = "http://10.10.16.105:15672/api/";
            String user = "admin";
            String pwd = "admin";
            String vhost = "mq_host";

//            MQHttp client = new MQHttp(url,user,pwd);
//            List<String> messageList = client.getMessageAllQueueList(queueName, 10);
//            System.out.println(messageList.toString());
//            messageList  = client.getMessageByVhostList(vhost, queueName, 10);
//            System.out.println(messageList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";

    }



}
