package com.qt.demo.wx.controller;

import com.qt.demo.common.redis.RedisCache;
import com.qt.demo.consts.RedisConst;
import com.qt.demo.manager.MyManager;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("wx")
public class WxMessageController {

    private MyManager myManager;
    private RedisCache redisCache;

    public WxMessageController(MyManager myManager, RedisCache redisCache) {
        this.myManager = myManager;
        this.redisCache = redisCache;
    }

    @GetMapping("/save")
    public String getMsg(@RequestParam(value = "signature",required = false ) String signature,
                              @RequestParam(value = "timestamp",required = false) String timestamp,
                              @RequestParam(value = "nonce",required = false) String nonce,
                              @RequestParam(value = "echostr",required = false) String echostr) {

        String s = "%s:%s:%s:%s";

        return String.format(s, signature, timestamp, nonce, echostr);
    }
    @PostMapping("/save")
    public String getMsgPost(@RequestParam(value = "signature",required = false ) String signature,
                         @RequestParam(value = "timestamp",required = false) String timestamp,
                         @RequestParam(value = "nonce",required = false) String nonce,
                         @RequestParam(value = "echostr",required = false) String echostr) {

        String s = "%s:%s:%s:%s";

        return String.format(s, signature, timestamp, nonce, echostr);
    }
    /**
     * 验证微信服务器     *      * @param response     * @param signature     * @param timestamp     * @param nonce     * @param echostr
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void wechatService(PrintWriter out,
                              HttpServletResponse response,
                              @RequestParam(value = "signature", required = false) String signature,
                              @RequestParam String timestamp,
                              @RequestParam String nonce,
                              @RequestParam String echostr) {
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    /**
     * 接收来自微信发来的消息     *      * @param out     * @param request     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
//        String responseMessage = wechatService.processRequest(request);
//        out.print(responseMessage);
//        out.flush();
    }

    /**
     * dom4j
     * 将xml转化为Map集合     *      * @param request     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        Element root = doc.getRootElement();
        @SuppressWarnings("unchecked") List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        try {
            ins.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return map;
    }

}
