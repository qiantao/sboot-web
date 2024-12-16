//package com.qt.demo.ws.controller;
//
//import cn.hutool.json.JSONUtil;
//import com.qt.demo.ws.WebSocketServer;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Description
// * @Author MuYang @Perfma
// * @Date 2022/03/15 17:27
// * @version: V1.0
// */
//@RestController
//@RequestMapping("/socket")
//public class SocketController {
//
//    @Resource
//    StringRedisTemplate template;
//    @Resource
//    private WebSocketServer webSocketServer;
//
//    /**
//     * 给指定用户推送消息
//     * @param userName 用户名
//     * @param message 消息
//     * @throws IOException
//     */
//    @RequestMapping(value = "/socket", method = RequestMethod.GET)
//    public void testSocket1(@RequestParam String userName, @RequestParam String message){
//        webSocketServer.sendInfo(userName, message);
//    }
//
//    /**
//     * 给所有用户推送消息
//     * @param message 消息
//     * @throws IOException
//     */
//    @RequestMapping(value = "/socket/all", method = RequestMethod.GET)
//    public void testSocket2(@RequestParam String message){
////        try {
////            webSocketServer.onMessage(message);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//    @RequestMapping(value = "/socket/redis", method = RequestMethod.GET)
//    public void sockerRedis(@RequestParam String userName, @RequestParam String message){
//        Map<String,String> map = new HashMap<>();
//        map.put("userName", userName);
//        map.put("msg", message);
//        template.convertAndSend("showNewestMsg",JSONUtil.toJsonStr(map));
//
//    }
//}
