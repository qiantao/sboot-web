package com.qt.demo.scheduler.config;

import com.qt.demo.scheduler.manager.SchedulerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/24 16:54
 * @version: V1.0
 */
@Slf4j
@Component
public class SchedulerInitHandler implements ApplicationRunner {

    @Autowired
    private SchedulerManager schedulerManager;
    @Value("${execTask}")
    private boolean execTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化 任务信息--------");
        if(execTask) {
            schedulerManager.init();
        }

    }
}
