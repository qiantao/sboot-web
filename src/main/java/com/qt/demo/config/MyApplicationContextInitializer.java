package com.qt.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Description
 * @Author MuYang @Perfma
 * @Date 2022/03/31 14:19
 * @version: V1.0
 */
@Slf4j
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        System.out.println("项目启动配置开始");
        System.out.println("项目启动配置开始");
        ConfigurableEnvironment environment = context.getEnvironment();
//        String url = environment.getProperty("xnature.http.url");
        System.setProperty("xsky.url","www.baidu.com");
        System.setProperty("cmdb.url","www.baidu.com");
        System.setProperty("test","true");
    }
}
