package com.qt.demo.controller.zentao;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.qt.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@Slf4j
@Controller
@RequestMapping("/zentao")
public class ZenTaoController {
    String zentaourl = "http://10.10.16.105:8090";
    String user = "root";
    String password = "Admin888.";
    Map<String,String> commonParam = Maps.newHashMap();

    @GetMapping("/productList")
    @ResponseBody
    public Object getProduct() {
        //获取session
        getsession();
        //登录
        login();

        //dobusiness
        Map<String,String> param = new HashMap<>();
        param.putAll(commonParam);
//        param.put("account",user);
//        param.put("password",password);

        String url = zentaourl + "/product-index.json";
        String result = HttpUtil.doGetJson(url + buildParam(param));

        return result;
    }

    private void login() {
        //登录
        Map<String,String> param = new HashMap<>();
        param.putAll(commonParam);
        param.put("account",user);
        param.put("password",password);

        String url = zentaourl + "/user-login.json";
        String result = HttpUtil.doGetJson(url + buildParam(param));

        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        String status = (String) map.get("status");
        if(!"success".equals(status) || !map.containsKey("user")){
            throw new RuntimeException("111");
        }
    }

    private String buildParam(Map<String,String> param){
        if(CollectionUtil.isEmpty(param)){
            return "";
        }
        StringBuilder bud = new StringBuilder("?");
        param.forEach((k,v)->{
            bud.append(k).append("=").append(v).append("&");
        });
        bud.setLength(bud.length()-1);
        return bud.toString();

    }

    private void getsession() {
        //获取session
        String url = zentaourl + "/api-getsessionid.json";
        String result = HttpUtil.doGetJson(url);
        //{"status":"success","data":"{\"title\":\"\",\"sessionName\":\"zentaosid\",\"sessionID\":\"5vv02npdnj6falvh96kkfdnn8f\",\"rand\":8690,\"pager\":null}","md5":"5f14ca11eb727bec01fccec43bf2cbf4"}
        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        Map<String,Object> data = JSONUtil.toBean(map.get("data").toString(),Map.class);
        String sessionName = (String) data.get("sessionName");
        String sessionID = (String) data.get("sessionID");
        if(!commonParam.containsKey(sessionName)){
            commonParam.put(sessionName,sessionID);
        }
    }


}
