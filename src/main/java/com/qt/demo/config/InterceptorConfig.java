package com.qt.demo.config;

import com.qt.demo.interceptor.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/23 14:49
 * @version: V1.0
 */
@Configuration
@EnableConfigurationProperties(InterceptorProperties.class)
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    private final RequestInterceptor requestInterceptor;
    private InterceptorProperties interceptorProperties;
    public InterceptorConfig(RequestInterceptor requestInterceptor, InterceptorProperties interceptorProperties) {
        this.requestInterceptor = requestInterceptor;
        this.interceptorProperties = interceptorProperties;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> urlList = interceptorProperties.getExcludePath();
        List<String> path = interceptorProperties.getPath();
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns(path)
                .excludePathPatterns(urlList);

        super.addInterceptors(registry);
    }

}
