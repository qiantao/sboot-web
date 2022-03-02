package com.qt.demo.common.mq.java;

import cn.hutool.core.collection.CollectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class MQConnectionUtil {
    public static String schem = "http";
//    public static String address = "127.0.0.1";
    public static String address = "10.10.16.105";
    public static Integer port = 5672;
    public static String queryUrl = schem+"://"+address+":"+15672+"/api/";

    public static String user = "admin";
    public static String pwd = "admin";
    public static String vhost = "mq_host";
    private static List<Connection> pool = new ArrayList<>();

    public static Connection getConnection(){
        Connection connection = null;
        try{
//            if(CollectionUtil.isNotEmpty(pool)){
//                connection = pool.get(0);
//                pool.remove(connection);
//                return connection;
//            }

            //定义一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置服务端地址（域名地址/ip）
            factory.setHost(address);
            //设置服务器端口号
            factory.setPort(5672);
            //设置虚拟主机(相当于数据库中的库)
            factory.setVirtualHost(vhost);
            //设置用户名
            factory.setUsername(user);
            //设置密码
            factory.setPassword(pwd);
            //设置超时时间
//            factory.setConnectionTimeout(3000);
            connection = factory.newConnection();
            return connection;
        }
        catch (Exception e){
            log.error("获取mq链接失败");
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection, Channel channel) {
        try {
            if (pool.size() < 8) {
                pool.add(connection);
            } else {
                channel.close();
                connection.close();
            }
        } catch (Exception e) {

        }
    }

}
