package com.qt.demo.handler;

import com.qt.demo.common.event.EventBaseListener;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName:
 * @Description: 解析bean操作
 * @author: QianTao
 * @date: 2021/09/07 09:45
 * @version: V1.0
 */
@Slf4j
@Component
public class BeanProcess implements BeanPostProcessor {
    @Value("${showLog:true}")
    private Boolean showLog;



    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(showLog) {
            log.info("After加载Bean : [{}]", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(showLog) {
            log.info("Before加载Bean : [{}]", beanName);
        }
        return bean;
    }
}
