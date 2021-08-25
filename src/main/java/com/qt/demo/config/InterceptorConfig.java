package com.qt.demo.config;

import com.qt.demo.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/23 14:49
 * @version: V1.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    private final RequestInterceptor requestInterceptor;

    public InterceptorConfig(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> urlList = new ArrayList<>();
//        urlList.add("/login");
//        urlList.add("/user/*");
//        registry.addInterceptor(requestInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(urlList);
//
//        super.addInterceptors(registry);
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

}
