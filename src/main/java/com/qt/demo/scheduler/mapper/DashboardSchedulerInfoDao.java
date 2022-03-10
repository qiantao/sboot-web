package com.qt.demo.scheduler.mapper;

import com.qt.demo.scheduler.domain.DashboardSchedulerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

;


/**
 * @ClassName:  DashboardSchedulerInfoDao
 * @Description: DashboardSchedulerInfoDao
 * @author: QianTao
 * @date:  2022/03/10 13:58:24
 * @version: V1.0
 */
@Mapper
public interface DashboardSchedulerInfoDao {
     /**
     * 根据实体进行查询多个
     * @param dashboardSchedulerInfo 查询实体
     * @return
     */
    List<DashboardSchedulerInfo> selectListByEntity(DashboardSchedulerInfo dashboardSchedulerInfo);

     /**
     * 根据实体进行查询一个
     * @param dashboardSchedulerInfo 查询实体
     * @return
     */
    DashboardSchedulerInfo selectByEntity(DashboardSchedulerInfo dashboardSchedulerInfo);

    /**
     * 更新字段不为空的数据
     * @param dashboardSchedulerInfo
     * @return
     */
    int updateByEntitySelective(DashboardSchedulerInfo dashboardSchedulerInfo);

    /**
     * 根据主键更新数据
     *
     * @param dashboardSchedulerInfoList
     * @return
     */
    int updateListById(List<DashboardSchedulerInfo> dashboardSchedulerInfoList);

    /**
     * 新增字段不为空的数据
     * @param dashboardSchedulerInfo
     * @return
     */
    int insertSelective(DashboardSchedulerInfo dashboardSchedulerInfo);

    /**
     * 新增字段不为空的数据
     * @param dashboardSchedulerInfoList
     * @return
     */
    int insertSelectiveList(List<DashboardSchedulerInfo> dashboardSchedulerInfoList);

    /**
     * 根据schedulerKey查询数据
     * @param schedulerKeyList
     * @return
     */
    List<DashboardSchedulerInfo> findListBySchedulerKeyList(List<String> schedulerKeyList);

    /**
     * 根据主健删除数据
     * @param idList
     * @return
     */
    int deleteByIdList(List<Long> idList);


    int updateBySchedulerKey(DashboardSchedulerInfo dashboardSchedulerInfo);
}