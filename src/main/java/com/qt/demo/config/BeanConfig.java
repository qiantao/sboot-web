package com.qt.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.greenrobot.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class BeanConfig {

    @Bean("event1")
    public EventBus eventBus1(){
        return new EventBus();
    }

    @Bean("event2")
    public EventBus eventBus2(){
        return new EventBus();
    }

    @Bean
    public RestHighLevelClient client() {
        // 异步httpclient连接延时配置
        HttpHost httpHost = new HttpHost("10.10.16.207",9200,"http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(30000);
            requestConfigBuilder.setSocketTimeout(30000);
            requestConfigBuilder.setConnectionRequestTimeout(3000000);
            return requestConfigBuilder;
        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(100);
            httpClientBuilder.setMaxConnPerRoute(100);
            return httpClientBuilder;
        });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));// 支持请求方式
        config.addAllowedOriginPattern("*");// 支持跨域
        config.setAllowCredentials(true);// cookie
        config.addAllowedHeader("*");// 允许请求头信息
        config.addExposedHeader("*");// 暴露的头部信息

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);// 添加地址映射
        return new CorsFilter(source);
    }
//....省略其他代码
}
