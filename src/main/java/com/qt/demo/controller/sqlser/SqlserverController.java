package com.qt.demo.controller.sqlser;

import com.qt.demo.common.mq.java.MQSendUtil;
import com.qt.demo.common.mq.spring.RabbitMqProducer;
import com.qt.demo.common.mq.spring.config.DirectRabbitConfig;
import com.qt.demo.common.mq.spring.config.ExchangeConfig;
import com.qt.demo.common.mq.spring.config.QueueConfig;
import com.qt.demo.common.sqlser.SqlServerConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sqlserver/")
public class SqlserverController {


    @GetMapping("query")
    public String query(){
        SqlServerConnectionUtil.getConnection();
        return "ok";
    }




}
