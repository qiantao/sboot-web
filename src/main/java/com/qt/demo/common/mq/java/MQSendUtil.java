package com.qt.demo.common.mq.java;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MQSendUtil {

    public static void send(String queueName,String message){
        try{
            //获取连接
            Connection connection = MQConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(queueName, false, false, false, null);
            //发送消息
            channel.basicPublish("", queueName, null, message.getBytes("utf-8"));
            channel.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }


    public static void bind(String queueName){
        try
        {
            //获取连接
            Connection connection = MQConnectionUtil.getConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(queueName, false, false, false, null);
            //定义消费者
            MyConsumer consumer = new MyConsumer(channel);

            //监听队列
            channel.basicConsume(queueName, true, consumer);
        }
        catch (IOException | ShutdownSignalException | ConsumerCancelledException e)
        {
            e.printStackTrace();
        }

    }

}
