//package com.qt.demo.ws;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Description
// * @Author MuYang @Perfma
// * @Date 2022/03/15 17:14
// * @version: V1.0
// */
//@Component
//@ServerEndpoint(value = "/socket/{name}")
//@Slf4j
//public class WebSocketServer {
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static AtomicInteger online = new AtomicInteger();
//
//    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
//    private static Map<String,Session> sessionPools = new HashMap<>();
//
//    /**
//     * 发送消息方法
//     * @param session 客户端与socket建立的会话
//     * @param message 消息
//     * @throws IOException
//     */
//    public void sendMessage(Session session, String message) throws IOException {
//        if(session != null){
//            session.getBasicRemote().sendText(message);
//        }
//    }
//
//
//    /**
//     * 连接建立成功调用
//     * @param session 客户端与socket建立的会话
//     * @param userName 客户端的userName
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam(value = "name") String userName){
//        session.setMaxIdleTimeout(30000);
//        sessionPools.put(userName, session);
//        addOnlineCount();
//        System.out.println(userName + "加入webSocket！当前人数为" + online);
//        try {
//            sendMessage(session, "欢迎" + userName + "加入连接！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 关闭连接时调用
//     * @param userName 关闭连接的客户端的姓名
//     */
//    @OnClose
//    public void onClose(@PathParam(value = "name") String userName){
//        sessionPools.remove(userName);
//        subOnlineCount();
//        System.out.println(userName + "断开webSocket连接！当前人数为" + online);
//    }
//
//    /**
//     * 收到客户端消息时触发（群发）
//     * @param message
//     * @throws IOException
//     */
//    @OnMessage
//    public void onMessage(@PathParam("name") String userName, String message) throws IOException{
//        log.info("userName:{} 收到 message:{}",userName,message);
//        for (Session session: sessionPools.values()) {
//            try {
//                sendMessage(session, message);
//            } catch(Exception e){
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }
//
//    /**
//     * 发生错误时候
//     * @param session
//     * @param throwable
//     */
//    @OnError
//    public void onError(Session session, Throwable throwable){
//        System.out.println("发生错误");
//        throwable.printStackTrace();
//    }
//
//    /**
//     * 给指定用户发送消息
//     * @param userName 用户名
//     * @param message 消息
//     * @throws IOException
//     */
//    public void sendInfo(String userName, String message){
//        Session session = sessionPools.get(userName);
//        try {
//            sendMessage(session, message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void addOnlineCount(){
//        online.incrementAndGet();
//    }
//
//    public static void subOnlineCount() {
//        online.decrementAndGet();
//    }
//}
