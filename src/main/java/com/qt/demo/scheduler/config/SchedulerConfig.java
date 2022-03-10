package com.qt.demo.scheduler.config;

import com.qt.demo.scheduler.domain.BaseScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@EnableScheduling
@Lazy(value = false)
public class SchedulerConfig implements SchedulingConfigurer {


    private Map<String, BaseScheduler> map = new HashMap<>();

    public SchedulerConfig(List<BaseScheduler> schedulerList){
        if(!CollectionUtils.isEmpty(schedulerList)){
            schedulerList.forEach(e->map.put(e.getSchedulerKey(),e));
        }
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (!CollectionUtils.isEmpty(map)){
            map.forEach((schedulerKey,scheduler)->{
                System.out.println(schedulerKey);
                taskRegistrar.addTriggerTask(scheduler, triggerContext -> {
                    CronTrigger trigger = new CronTrigger(scheduler.getCron());
                    return trigger.nextExecutionTime(triggerContext);
                });
            });
        }

    }


    public void setCronBySchedulerKey(String schedulerKey,String cron){
        if(map.containsKey(schedulerKey)){
            map.get(schedulerKey).setCron(cron);
        }

    }
}