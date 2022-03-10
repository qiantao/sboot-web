package com.qt.demo.scheduler.service.impl;

import com.google.common.collect.Lists;
import com.qt.demo.scheduler.domain.DashboardSchedulerInfo;
import com.qt.demo.scheduler.mapper.DashboardSchedulerInfoDao;
import com.qt.demo.scheduler.service.DashboardSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardSchedulerServiceImpl implements DashboardSchedulerService {

    @Autowired
    private DashboardSchedulerInfoDao dashboardSchedulerInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrModScheduler(List<DashboardSchedulerInfo> dashboardSchedulerInfoList) {
        if(CollectionUtils.isEmpty(dashboardSchedulerInfoList)){
            return 0;
        }
        List<String> schedulerKeyList = dashboardSchedulerInfoList.stream().map(DashboardSchedulerInfo::getSchedulerKey).collect(Collectors.toList());
        //查询已存在的任务
        List<DashboardSchedulerInfo> exitSchedulerList = findListBySchedulerKeyList(schedulerKeyList);
        Map<String, DashboardSchedulerInfo> schedulerKeyEntityListMap = exitSchedulerList.stream().collect(Collectors.toMap(DashboardSchedulerInfo::getSchedulerKey, Function.identity()));

        List<DashboardSchedulerInfo> addList = new ArrayList<>();
        List<DashboardSchedulerInfo> modList = new ArrayList<>();
        for (DashboardSchedulerInfo dashboardSchedulerInfo : dashboardSchedulerInfoList) {
            if(schedulerKeyEntityListMap.containsKey(dashboardSchedulerInfo.getSchedulerKey())){
                DashboardSchedulerInfo exitEntity = schedulerKeyEntityListMap.get(dashboardSchedulerInfo.getSchedulerKey());
                dashboardSchedulerInfo.setId(exitEntity.getId());
                modList.add(dashboardSchedulerInfo);
                continue;
            }
            addList.add(dashboardSchedulerInfo);
        }

        int insertCount = 0;
        if(!CollectionUtils.isEmpty(addList)) {
            insertCount = dashboardSchedulerInfoDao.insertSelectiveList(addList);
        }
        int updateCount = 0;
        if(!CollectionUtils.isEmpty(modList)) {
            updateCount = dashboardSchedulerInfoDao.updateListById(modList);
        }
        return insertCount + updateCount;
    }

    @Override
    public List<DashboardSchedulerInfo> findListBySchedulerKeyList(List<String> schedulerKeyList) {
        if(CollectionUtils.isEmpty(schedulerKeyList)){
            return Lists.newArrayList();
        }
        return dashboardSchedulerInfoDao.findListBySchedulerKeyList(schedulerKeyList);
    }

    @Override
    public int init(List<DashboardSchedulerInfo> dashboardSchedulerInfoList) {
        if(CollectionUtils.isEmpty(dashboardSchedulerInfoList)){
            return 0;
        }
        List<String> schedulerKeyList = dashboardSchedulerInfoList.stream().map(DashboardSchedulerInfo::getSchedulerKey).collect(Collectors.toList());
        //查询已存在的任务
        List<DashboardSchedulerInfo> exitSchedulerList = findListBySchedulerKeyList(schedulerKeyList);
        Map<String, DashboardSchedulerInfo> schedulerKeyEntityListMap = exitSchedulerList.stream().collect(Collectors.toMap(DashboardSchedulerInfo::getSchedulerKey, Function.identity()));

        List<DashboardSchedulerInfo> addList = new ArrayList<>();
        List<DashboardSchedulerInfo> modList = new ArrayList<>();
        List<Long> deleteIdList = new ArrayList<>();
        for (DashboardSchedulerInfo dashboardSchedulerInfo : dashboardSchedulerInfoList) {
            if(schedulerKeyEntityListMap.containsKey(dashboardSchedulerInfo.getSchedulerKey())){
                DashboardSchedulerInfo exitEntity = schedulerKeyEntityListMap.get(dashboardSchedulerInfo.getSchedulerKey());
                dashboardSchedulerInfo.setId(exitEntity.getId());
                modList.add(dashboardSchedulerInfo);
                schedulerKeyEntityListMap.remove(dashboardSchedulerInfo.getSchedulerKey());
                continue;
            }
            addList.add(dashboardSchedulerInfo);
        }
        if(!CollectionUtils.isEmpty(schedulerKeyEntityListMap)){
            schedulerKeyEntityListMap.forEach((k,v)->{
                deleteIdList.add(v.getId());
            });
        }


        int insertCount = 0;
        if(!CollectionUtils.isEmpty(addList)) {
           insertCount = dashboardSchedulerInfoDao.insertSelectiveList(addList);
        }
        int updateCount = 0;
        if(!CollectionUtils.isEmpty(modList)) {
            updateCount = dashboardSchedulerInfoDao.updateListById(modList);
        }
        int deleteCount = 0;

        if(!CollectionUtils.isEmpty(deleteIdList)){
            deleteCount = dashboardSchedulerInfoDao.deleteByIdList(deleteIdList);
        }
        return insertCount + updateCount + deleteCount;

    }

    @Override
    public int updateBySchedulerKey(DashboardSchedulerInfo dashboardSchedulerInfo) {
        return dashboardSchedulerInfoDao.updateBySchedulerKey(dashboardSchedulerInfo);
    }
}
