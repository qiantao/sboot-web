package com.qt.demo.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/24 15:00
 * @version: V1.0
 */
@Slf4j
public class HttpUtil {

    public static <T> T doPostJson(String url, String requestBody,Class<T> className){
        return doPostJson(url,requestBody,null,className);
    }

    public static <T> T doPostJson(String url, String requestBody,Map<String,String> headerMap, Class<T> className){
//
        String result = doPostJson(url, requestBody,headerMap);
        if (StringUtils.isEmpty(result) || !result.startsWith("{")) {
            return null;
        }

        return JSONUtil.toBean(result,className);
    }


    public static String doPostJson(String url, String requestBody, Map<String,String> headerMap){


        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach((k,v)->headers.add(k,v));
        }
        HttpMethod method = HttpMethod.POST;
        // 以JSON的方式提交
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
        log.info("请求url = {}",url);
        log.info("请求body = {}",requestBody);
        log.info("请求res = {}",body);
        return body;
    }

    public static String doGetJson(String url){

        String requestBody=null;
        Map<String,String> headerMap = new HashMap<>();

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach((k,v)->headers.add(k,v));
        }
        HttpMethod method = HttpMethod.GET;
        // 以JSON的方式提交
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
        log.info("请求url = {}",url);
        log.info("请求body = {}",requestBody);
        log.info("请求res = {}",body);
        return body;
    }
}
