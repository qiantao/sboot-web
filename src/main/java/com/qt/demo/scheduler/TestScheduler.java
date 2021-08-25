package com.qt.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/06 16:06
 * @version: V1.0
 */
@Component
@Slf4j
public class TestScheduler {

    @Scheduled(fixedDelay = 60000)
    public void logScheduler(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        for (int i = 0; i < 10; i++) {
//            log.info(Thread.currentThread().getName() +" %%%%% "+sdf.format(new Date())+"****"+i);
//        }
    }
}
