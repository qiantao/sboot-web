package com.qt.demo.proxy;

import cn.hutool.json.JSONUtil;
import com.qt.demo.annotation.proxy.MehodAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
    @Pointcut("execution(* com.qt.demo.service.proxy.ProxyService.testProxy())")
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


    @Pointcut(value = "@annotation(com.qt.demo.annotation.proxy.ProxyAnnotation)")
    public void cutByAnnotation(){
        log.info("cutByAnnotation---");
    }
    @Before("cutByAnnotation()")
    public void cutByAnnotationBefore(JoinPoint joinPoint){
        log.info("cutByAnnotation Before---");

    }
    @After("cutByAnnotation()")
    public void cutByAnnotationAfter(JoinPoint joinPoint){
        log.info("cutByAnnotation After---");
    }
    @AfterReturning(value = "cutByAnnotation()",returning = "o")
    public void afterReturningAnnotation(JoinPoint joinPoint,Object o) throws Exception {
        log.info("cutByAnnotation after return---");
        log.info("cutByAnnotation after {}", JSONUtil.toJsonStr(o));
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getTarget());
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        MehodAnnotation annotation1 = methodSignature.getMethod().getAnnotation(MehodAnnotation.class);
        log.info(JSONUtil.toJsonStr(annotation1));
        log.info(args.toString());
    }
    @AfterThrowing("cutByAnnotation()")
    public void afterThrowingAnnotation(JoinPoint joinPoint) {
        log.info("cutByAnnotation after throwing---");
    }



    //---------

//    @Around(value = "@annotation(prox)")
//    public String proxyAnnotation(ProceedingJoinPoint joinPoint, ProxyAnnotation prox) throws Throwable {
//        log.info("proxy by annotation ");
//        //执行原本方法
////        String result = joinPoint.proceed().toString();
//
//        return null;
//    }



}
