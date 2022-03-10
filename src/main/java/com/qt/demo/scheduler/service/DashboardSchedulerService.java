package com.qt.demo.scheduler.service;

import com.qt.demo.scheduler.domain.DashboardSchedulerInfo;

import java.util.List;

public interface DashboardSchedulerService {

    int addOrModScheduler(List<DashboardSchedulerInfo> dashboardSchedulerInfoList);

    List<DashboardSchedulerInfo> findListBySchedulerKeyList(List<String> schedulerKeyList);

    int init(List<DashboardSchedulerInfo> dashboardSchedulerInfoList);

    int updateBySchedulerKey(DashboardSchedulerInfo dashboardSchedulerInfo);
}
