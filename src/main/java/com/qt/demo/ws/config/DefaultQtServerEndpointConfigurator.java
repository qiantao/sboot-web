//package com.qt.demo.ws.config;
//
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.websocket.server.DefaultServerEndpointConfigurator;
//import org.springframework.context.annotation.Configuration;
//
//import javax.websocket.HandshakeResponse;
//import javax.websocket.server.HandshakeRequest;
//import javax.websocket.server.ServerEndpointConfig;
//
//@Configuration
//@Slf4j
//public class DefaultQtServerEndpointConfigurator extends DefaultServerEndpointConfigurator {
//    private final long sendPeriod = 500; // 发送周期（心跳间隔）
//    private final long maxIdleTime = 1000; // 最大空闲时间
//
//    public DefaultQtServerEndpointConfigurator() {
//        log.info("------------aaaaaaaaaa");
//    }
//
//    @Override
//    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//        // 修改握手响应以设置心跳参数
//        response.getHeaders().put("WebSocket-SendPeriod", Lists.newArrayList(Long.toString(sendPeriod)));
//        response.getHeaders().put("WebSocket-MaxIdleTime", Lists.newArrayList(Long.toString(maxIdleTime)));
////        fetchContainerDefaultConfigurator().modifyHandshake(sec, request, response)
//    }
//}