//package com.qt.demo.ws.config;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.qt.demo.ws.WebSocketServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * 消息监听对象，接收订阅消息
// * @author csf
// * @date 2020年8月13日
// */
//@Component
//public class RedisReceiver implements MessageListener {
//    Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    WebSocketServer webSocketServer;
//
//    /**
//     * 处理接收到的订阅消息
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern)
//    {
//        String channel = new String(message.getChannel());// 订阅的频道名称
//        String msg = "";
//        try
//        {
//            msg = new String(message.getBody(), "UTF-8");//注意与发布消息编码一致，否则会乱码
//            if (StrUtil.isNotBlank(msg)){
//                if ("showNewestMsg".endsWith(channel))// 最新消息
//                {
//                    log.info("收到redis广播:{}",msg);
//                    JSONObject jsonObject = JSONUtil.parseObj(msg);
//                    webSocketServer.sendInfo(jsonObject.get("userName").toString(),jsonObject.get("msg").toString());
//                }else{
//                    //TODO 其他订阅的消息处理
//                }
//
//            }else{
//                log.info("消息内容为空，不处理。");
//            }
//        }
//        catch (Exception e)
//        {
//            log.error("处理消息异常："+e.toString());
//            e.printStackTrace();
//        }
//    }
//}
