package com.qt.demo.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 11:27
 * @version: V1.0
 */
@Slf4j
public class JavaProxyHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("====================================================");
        log.info("代理者的对象：" + proxy.getClass().getName());
        // 被代理的接口
        Class<?> targetClass = method.getDeclaringClass();
        log.info("被代理的接口类：" + targetClass.getName());
        log.info("被代理的方法：" + method.getName());
        if(args == null) {
            return "代理结果：";
        }

        log.info("被代理的调用过程参数类型：");
        Arrays.asList(args).stream().forEach((item) -> {
            log.info("方法类型：" + item.getClass().getName());
        });

        // 在这里可以返回调用结果
        return null;
    }
}
