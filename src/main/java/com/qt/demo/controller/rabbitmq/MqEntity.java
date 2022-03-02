package com.qt.demo.controller.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MqEntity {
    private String exchange;
    private String queueName;
    private String routingKey;
    private BuiltinExchangeType builtinExchangeType = BuiltinExchangeType.HEADERS;
    private Map<String,Object> header = new HashMap<>();
    private String message;



}
