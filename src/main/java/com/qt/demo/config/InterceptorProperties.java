package com.qt.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/25 16:07
 * @version: V1.0
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "interceptor")
public class InterceptorProperties {
    public List<String> excludePath;
    public List<String> path;

}
