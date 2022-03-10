package com.qt.demo.scheduler;

import com.qt.demo.scheduler.domain.BaseScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class SchedulerOne extends BaseScheduler {

    @Override
    public boolean doTask() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("ONE 真正的任务执行逻辑。。。{}",sdf.format(new Date()));
        return true;
    }

    @Override
    public void init() {
        super.setCron("0/5 * * * * ?");
        super.setSchedulerKey(this.getClass().getName());
    }
}
