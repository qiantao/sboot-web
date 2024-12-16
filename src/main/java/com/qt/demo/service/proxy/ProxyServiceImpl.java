package com.qt.demo.service.proxy;

import cn.hutool.json.JSONUtil;
import com.qt.demo.annotation.proxy.MehodAnnotation;
import com.qt.demo.annotation.proxy.ProxyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 14:07
 * @version: V1.0
 */
@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService{

    @Override
    public String testProxy() {
        log.info("proxy-----ing--------");
        return "success";
    }

    @Override
    @ProxyAnnotation
    @MehodAnnotation(qtName = "aaaa")
    public String methodProxy(Class<SubMethodService> subProxyServiceClass1) {

        Class<? extends ProxyServiceImpl> subProxyServiceClass = this.getClass();
        Method[] declaredMethods = subProxyServiceClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            MehodAnnotation annotation = declaredMethod.getAnnotation(MehodAnnotation.class);
            if(annotation == null) {
                System.out.println("annotation is null");
            }
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            try {
                Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
                memberValues.setAccessible(true);
                Object o = memberValues.get(invocationHandler);
                log.info("methodProxy 方法内:{}",JSONUtil.toJsonStr(o));
                Map o1 = (Map) o;
                o1.put("qtName","1243");
                return o.toString();
            }catch (Exception e){

            }

        }
        return "fff";
    }


    @Override
    @ProxyAnnotation
    public String testAnnotation(String type){
        log.info("proxy---ing---annotation  type = {}",type);
        return "annotation-success";
    }
}
