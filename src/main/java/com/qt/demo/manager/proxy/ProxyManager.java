package com.qt.demo.manager.proxy;

import com.qt.demo.proxy.JavaProxyHandler;
import com.qt.demo.service.proxy.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 11:11
 * @version: V1.0
 */
@Component
@Slf4j
public class ProxyManager {
    @Autowired
    private ProxyService proxyService;

    public String javaProxy(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //这是代理处理器
        JavaProxyHandler invocationHandler = new JavaProxyHandler();
        Object proxy = Proxy.newProxyInstance(classLoader,new Class<?>[]{ProxyService.class}, invocationHandler);
        ProxyService testService = (ProxyService) proxy;
        return testService.testProxy();
    }

    public String aspectProxy(String type) {
        switch (type){
            case "annotation" : return proxyService.testAnnotation(type);
            default:
                return proxyService.testProxy();
        }
    }

}
