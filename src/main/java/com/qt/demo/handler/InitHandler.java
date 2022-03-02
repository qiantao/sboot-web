package com.qt.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/24 16:54
 * @version: V1.0
 */
@Slf4j
@Component
public class InitHandler implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner ->> 项目启动成功！～～～～～");

    }
}
