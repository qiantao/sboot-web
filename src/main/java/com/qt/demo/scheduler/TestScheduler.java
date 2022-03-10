package com.qt.demo.scheduler;

import com.qt.demo.common.kafka.KafKaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KafKaProducer kafKaProducer;


    int i = 0;
//    @Scheduled(fixedDelay = 60000)
    public void sendKafkaMessage(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
//        kafKaProducer.send(format+"qt测试。。。");
        }catch (Exception e){
            log.error("sendKafkaMessage err {}",e.getMessage());
        }
    }


//    @Scheduled(cron = "0/30 * * * * ?")
    public void cronTask(){
        try {
            long l = System.currentTimeMillis()+7000;
            while(System.currentTimeMillis() < l && i%3 ==0){

            }
            log.info("{},{}",Thread.currentThread().getName(),i);
            i++;
        }catch (Exception e){
            log.error("cronTask err {}",e.getMessage());
        }
    }
}
