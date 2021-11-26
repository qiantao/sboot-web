package com.qt.demo.common.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 推送数据到process kafka
 */
@Component
public class KafKaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafKaProducer.class);


    private final KafkaTemplate<byte[], byte[]> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String producerTopic = "workflow_result";

    @Autowired
    public KafKaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * 推送kafka对象
     *
     * @param message 推送kafka的事件对象
     */
    public void send(String message) {
        kafkaTemplate.send(producerTopic, message.getBytes()).addCallback(new ListenableFutureCallback<SendResult<byte[], byte[]>>() {
            @Override
            public void onFailure(@Nullable Throwable throwable) {
                log.error("KafkaProducer#send send msg fail:{}, vendor:[{}]", message);
            }

            @Override
            public void onSuccess(SendResult<byte[], byte[]> sendResult) {
                log.info("KafkaProducer#send send msg success!, vendor:[{}]", message);
            }
        });

    }
}
