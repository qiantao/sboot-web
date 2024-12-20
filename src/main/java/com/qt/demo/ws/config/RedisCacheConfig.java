//package com.qt.demo.ws.config;
//
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//
//@Configuration
//@EnableCaching
//public class RedisCacheConfig {
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        // 可以添加多个messageListener，配置不同的交换机
//        container.addMessageListener(listenerAdapter, new PatternTopic("showNewestMsg"));// 订阅最新消息频道
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
//        // 消息监听适配器
//        return new MessageListenerAdapter(receiver, "onMessage");
//    }
//
//    @Bean
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }
//}
