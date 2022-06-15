package com.qt.demo.scheduler.domain;

import com.qt.demo.scheduler.service.DashboardSchedulerService;
import com.qt.demo.util.CronExpression;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Data
@Component
public abstract class BaseScheduler implements Runnable{

    @Autowired
    private DashboardSchedulerService dashboardSchedulerService;

    @Value("${execTask}")
    private boolean execTask;

    private String cron = "";

    private String schedulerKey = "";

    private String schedulerName ="";

    private int retry_time = 1;

    public BaseScheduler(){
        init();
    }

    @Override
    public void run() {

        if(!execTask){
            return;
        }
        DashboardSchedulerInfo dashboardSchedulerInfo = new DashboardSchedulerInfo();
        dashboardSchedulerInfo.setCurrentExecTime(new Date());
        dashboardSchedulerInfo.setCron(cron);
        dashboardSchedulerInfo.setRetryTime(retry_time);
        dashboardSchedulerInfo.setNextExecTime(CronExpression.getNextExecTime(cron));
        dashboardSchedulerInfo.setSchedulerKey(schedulerKey);
        dashboardSchedulerInfo.setSchedulerName(schedulerName);
        int count = 0;
        boolean res = false;
        while (count < retry_time) {
            try {
                res = doTask();

                if(res){
                    break;
                }

            } catch (Exception e) {
                log.error("任务执行失败");
                res = false;
            }finally {
                count++;
            }
        }
        dashboardSchedulerInfo.setResult(res ?"success":"fail");
        addRecord(dashboardSchedulerInfo);
    }

    /**
     * 执行任务
     * @return
     */
    public abstract boolean doTask();

    /**
     * 初始化任务信息
     */
    public abstract void init();


    /**
     * 任务执行完毕处理逻辑
     * @param dashboardSchedulerInfo 任务执行结果
     */
    public void addRecord(DashboardSchedulerInfo dashboardSchedulerInfo){
        dashboardSchedulerService.updateBySchedulerKey(dashboardSchedulerInfo);
    }



}
