package com.qt.demo.proxy;

import cn.hutool.core.convert.Convert;
import com.qt.demo.annotation.proxy.ProxyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 14:34
 * @version: V1.0
 */
@Aspect
@Slf4j
@Component
public class SpringProxyHandler {

    /**
     * https://blog.csdn.net/zhengchao1991/article/details/53391244?utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-1.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromMachineLearnPai2~default-1.control
     *  *(..) 任何方法
     *  *() 任何无参方法
     *  *(*) 1个参数的方法
     *   .........
     */
    @Pointcut("execution(* com.qt.demo.service.proxy.ProxyService.*())")
    public void executeService(){
        log.info("aspect->execute method ");
    }


    // 前置事件
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        log.info("前置事件!");
    }

    //后置事件
    @After("executeService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("后置事件!");
    }
    @AfterReturning(value = "executeService()")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("afterReturning advise");
    }

    @AfterThrowing(value = "executeService()")
    public void afterThrowing(JoinPoint joinPoint) {
        log.info("afterThrowing advise");
    }

//------------------


    @Around(value = "@annotation(prox)")
    public String proxyAnnotation(ProceedingJoinPoint joinPoint, ProxyAnnotation prox) throws Throwable {
        log.info("aspect->Around annotation method ");
        //执行原本方法
        String result = joinPoint.proceed().toString();

        return result;
    }
}
