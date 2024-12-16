package com.qt.demo.entity;

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
@ConfigurationProperties(prefix = "calculate")
public class CalculateConfig {
    public List<DataFilterConfig> notBatchConfig;

}
