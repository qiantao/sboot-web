package com.qt.demo.scheduler.manager;

import com.qt.demo.scheduler.domain.BaseScheduler;
import com.qt.demo.scheduler.domain.DashboardSchedulerInfo;
import com.qt.demo.scheduler.service.DashboardSchedulerService;
import com.qt.demo.util.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchedulerManager {
    @Autowired
    private List<BaseScheduler> schedulerList;

    @Autowired
    private DashboardSchedulerService dashboardSchedulerService;

    /**
     * 初始化任务数据
     */
    public void init(){
        List<DashboardSchedulerInfo> dashboardSchedulerInfoList = new ArrayList<>();

        for (BaseScheduler baseScheduler : schedulerList) {
            DashboardSchedulerInfo dashboardSchedulerInfo = DashboardSchedulerInfo.builder()
                    .schedulerKey(baseScheduler.getSchedulerKey())
                    .cron(baseScheduler.getCron())
                    .creatorId(0L)
                    .modifierId(0L)
                    .schedulerName(baseScheduler.getSchedulerName())
                    .retryTime(1)
                    .result("")
                    .nextExecTime(CronExpression.getNextExecTime(baseScheduler.getCron()))
                    .build();
            dashboardSchedulerInfoList.add(dashboardSchedulerInfo);

        }

        dashboardSchedulerService.init(dashboardSchedulerInfoList);


    }
}
