package com.qt.demo.common.mq.java;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class MQSendUtil {

    public static boolean send(String exchange,
                            String queueName,
                            String routingKey,
                            BuiltinExchangeType builtinExchangeType,
                            Map<String,Object> header,
                            String message){
        try{
            //获取连接
            Connection connection = MQConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(queueName, true, false, false, null);
            // 2.为通道声明direct类型的exchange
//            channel.exchangeDeclare(exchange, builtinExchangeType);
            if(StringUtils.isNotBlank(queueName) && !routingKey.contains("*")) {
//                channel.queueBind(queueName,exchange,routingKey);
            }

            AMQP.BasicProperties prop = new AMQP.BasicProperties();
            AMQP.BasicProperties build = prop.builder().headers(header).build();
            //发送消息
//            if(StringUtils.isBlank(routingKey)) {
//                channel.basicPublish(exchange, queueName, build, message.getBytes("utf-8"));
//            }else{
                channel.basicPublish(exchange, routingKey, build, message.getBytes("utf-8"));

//            }
            channel.close();
            connection.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {

        }

    }

    public static GetResponse consumerOne(String queueName, Boolean ack){
        try
        {
            //获取连接
            Connection connection = MQConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
//            channel.queueDeclare(queueName, false, false, false, null);
            //定义消费者
//            MyConsumer consumer = new MyConsumer(channel);
//
//            //监听队列
//            channel.basicConsume(queueName, ack, consumer);
            GetResponse getResponse = channel.basicGet(queueName, ack);

            return getResponse;
        }
        catch (IOException | ShutdownSignalException | ConsumerCancelledException e)
        {
            e.printStackTrace();
        }
        return null;

    }
    public static void consumer(String queueName, Boolean ack){
        try
        {
            //获取连接
            Connection connection = MQConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
//            channel.queueDeclare(queueName, false, false, false, null);
//            定义消费者
            MyConsumer consumer = new MyConsumer(channel);

            //监听队列
            channel.basicConsume(queueName, ack, consumer);

        }
        catch (IOException | ShutdownSignalException | ConsumerCancelledException e)
        {
            e.printStackTrace();
        }

    }

}
