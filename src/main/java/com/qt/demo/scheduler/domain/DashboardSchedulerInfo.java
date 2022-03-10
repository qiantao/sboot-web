package com.qt.demo.scheduler.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName:  DashboardSchedulerInfo   
 * @Description: 任务信息表(t_dashboard_scheduler_info)实体类   
 * @author: QianTao 
 * @date:   2022/03/10 13:58:24
 * @version: V1.0   
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSchedulerInfo implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long modifierId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 任务key
     */
    private String schedulerKey;

    /**
     * 任务名称
     */
    private String schedulerName;

    /**
     * 任务表达式
     */
    private String cron;

    /**
     * 重试次数
     */
    private Integer retryTime;

    /**
     * 最近执行时间
     */
    private Date currentExecTime;

    /**
     * 下一次执行时间
     */
    private Date nextExecTime;

    /**
     * 执行结果
     */
    private String result;






}