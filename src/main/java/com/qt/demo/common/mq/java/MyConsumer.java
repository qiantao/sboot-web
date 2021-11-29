package com.qt.demo.common.mq.java;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MyConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery

            (String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                               byte[] body) throws IOException
    {
        String message = new String(body, "utf-8");
        log.info("[Receive]：{}",message);
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        log.info("[Cancel]：{}",consumerTag);
    }
}
