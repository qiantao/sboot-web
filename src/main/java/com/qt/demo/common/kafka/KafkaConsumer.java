package com.qt.demo.common.kafka;

import cn.hutool.json.JSONUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaHandler.class);


    /**
     * 事件数据消费
     *
     * @param records List<ConsumerRecord<?, ?>>
     * @param ack     Acknowledgment
     */
//    @KafkaListener(topics = { "workflow_result"})
    public void eventConsume(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {

//        log.error("消费消息成功： {}", JSONUtil.toJsonStr(records));
        try {
//            List<ElevatorEventObject> eventObjectList = new ArrayList<>();
            for (ConsumerRecord<?, ?> consumerRecord : records) {
                Optional<?> message = Optional.ofNullable(consumerRecord.value());
                if (message.isPresent()) {
                    String msg = new String((byte[]) message.get());
//                    ElevatorEventObject eventObject = json.deserialize(msg, ElevatorEventObject.class);
//                    eventObjectList.add(eventObject);
//                    log.error("消费消息成功： {}",msg);
                }
            }
//
//            processor.process(eventObjectList);
        } catch (Exception e) {
            log.error("consume message failed! error={}", e.getMessage());
            e.printStackTrace();
        } finally {
            ack.acknowledge();
        }
    }

}
