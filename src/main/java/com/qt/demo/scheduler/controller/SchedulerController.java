package com.qt.demo.scheduler.controller;

import com.qt.demo.scheduler.config.SchedulerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件处理接口
 */
@RestController
@RequestMapping("/scheduler")
@Slf4j
public class SchedulerController {

    @Autowired
    private SchedulerConfig schedulerConfig;

    @GetMapping("/setCron")
    public String upload(@RequestParam("cron") String cron) {

        schedulerConfig.setCronBySchedulerKey("com.perfma.dashboard.scheduler.SchedulerOne","0/2 * * * * ?");
        return "success";
    }


}
