package com.qt.demo.controller.rabbitmq;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.qt.demo.common.mq.java.MQSendUtil;
import com.qt.demo.common.mq.spring.RabbitMqProducer;
import com.qt.demo.common.mq.spring.config.DirectRabbitConfig;
import com.qt.demo.common.mq.spring.config.ExchangeConfig;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        rabbitMqProducer.sendDirectRouting(ExchangeConfig.TestTopicExchange, DirectRabbitConfig.TestDirectRouting,map.toString());
//        rabbitMqProducer.sendDirectRouting(ExchangeConfig.TestDirectExchange, DirectRabbitConfig.TestDirectRouting,map.toString());
//        rabbitMqProducer.sendQueue(QueueConfig.TestDirectQueue,map.toString());
        return "ok";
    }

    @PostMapping("send1")
    public String send1(@RequestBody MqEntity mqEntity){
        boolean send = false;
        try {
            String exchange = "testdirect";//mqEntity.getExchange();
            String queueName = mqEntity.getQueueName();
            String routingKey= mqEntity.getRoutingKey();
            BuiltinExchangeType builtinExchangeType = BuiltinExchangeType.DIRECT;//mqEntity.getBuiltinExchangeType();
            Map<String,Object> header = mqEntity.getHeader();
            String message = mqEntity.getMessage();
            send = MQSendUtil.send(exchange, queueName, routingKey, builtinExchangeType, header, message);

            log.info("发送成功");
//            MQSendUtil.bind(queueName);
//            String url = "http://127.0.0.1:15672/api/";
//            String user = "admin";
//            String pwd = "admin";
//            String vhost = "mq_host";
//
//            MQHttp client = new MQHttp(url,user,pwd);
//            List<String> messageList = client.getMessageAllQueueList(queueName, 10);
//            System.out.println(messageList.toString());
//            messageList  = client.getMessageByVhostList(vhost, queueName, 10);
//            System.out.println(messageList.toString());

        } catch (Exception e) {
            e.printStackTrace();
            send = false;
        }

        return send ? "ok":"fail";

    }
    @PostMapping("consumer")
    public String consumer(@RequestBody MqEntity mqEntity){
        boolean send = true;
        Map<String,Object> resultMap = new HashMap<>();
        try {
            String queueName = mqEntity.getQueueName();
            GetResponse getResponse = MQSendUtil.consumerOne(queueName, true);

            if(getResponse!=null){
                resultMap.put("body",new String(getResponse.getBody()));
                Map<String, Object> headers = new HashMap<>();
                if(getResponse.getProps()!=null) {
                    headers = getResponse.getProps().getHeaders();
                }
                resultMap.put("header", headers);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap.toString();
    }

    @PostMapping("send/proceedEmpty")
    public String sendPass(@RequestBody Map<String,String> message){
        boolean send = false;
        try {
            String exchange = "testdirect";//mqEntity.getExchange();
            String queueName = "yaoyao1";
            String routingKey= "";
            BuiltinExchangeType builtinExchangeType = BuiltinExchangeType.DIRECT;//mqEntity.getBuiltinExchangeType();
            Map<String,Object> header = new HashMap<>();
            message.put("reportResult","通过");
            send = MQSendUtil.send(exchange, queueName, routingKey, builtinExchangeType, header, JSONUtil.toJsonStr(message));
            log.info("通过发送成功");

        } catch (Exception e) {
            e.printStackTrace();
            send = false;
        }

        return send ? "ok":"fail";

    }

    @PostMapping("send/abort")
    public String sendNoPass(@RequestBody Map<String,String> message){
        boolean send = false;
        try {
            String exchange = "testdirect";
            String queueName = "yaoyao1";
            String routingKey= "";
            BuiltinExchangeType builtinExchangeType = BuiltinExchangeType.DIRECT;//mqEntity.getBuiltinExchangeType();
            Map<String,Object> header = new HashMap<>();
            message.put("reportResult","不通过");
            send = MQSendUtil.send(exchange, queueName, routingKey, builtinExchangeType, header, JSONUtil.toJsonStr(message));

            log.info("不通过 发送成功");

        } catch (Exception e) {
            e.printStackTrace();
            send = false;
        }

        return send ? "ok":"fail";

    }

}
