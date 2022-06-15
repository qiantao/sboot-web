package com.qt.demo.scheduler.controller;
import java.util.Date;

import com.qt.demo.scheduler.config.SchedulerConfig;
import com.qt.demo.scheduler.domain.DashboardSchedulerInfo;
import com.qt.demo.scheduler.mapper.DashboardSchedulerInfoDao;
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
    @Autowired
    private DashboardSchedulerInfoDao dashboardSchedulerInfoDao;

    @GetMapping("/setCron")
    public String upload(@RequestParam("cron") String cron) {

        schedulerConfig.setCronBySchedulerKey("com.perfma.dashboard.scheduler.SchedulerOne","0/2 * * * * ?");
        return "success";
    }

    @GetMapping("/add")
    public String add(){
        int count = 10002;
        for (int i = count; i <= count+1000000; i++) {
            DashboardSchedulerInfo dashboardSchedulerInfo = new DashboardSchedulerInfo();
            dashboardSchedulerInfo.setCreatorId(0L+i);
            dashboardSchedulerInfo.setModifierId(0L+i);
            dashboardSchedulerInfo.setSchedulerKey("key"+i);
            dashboardSchedulerInfo.setSchedulerName("名称"+i);
            dashboardSchedulerInfo.setCron("0/"+i+" * * * * ?");
            dashboardSchedulerInfo.setRetryTime(1);
            dashboardSchedulerInfo.setCurrentExecTime(new Date());
            dashboardSchedulerInfo.setNextExecTime(new Date());
            dashboardSchedulerInfo.setResult("风刀霜剑发生放");


            dashboardSchedulerInfoDao.insertSelective(dashboardSchedulerInfo);
//            System.out.println(i);
        }
        return "ok";
    }
}
