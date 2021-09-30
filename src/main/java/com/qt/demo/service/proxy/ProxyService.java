package com.qt.demo.service.proxy;

import com.qt.demo.annotation.proxy.ProxyAnnotation;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 14:06
 * @version: V1.0
 */
public interface ProxyService {
    String testAnnotation(String type);

    String testProxy();
}
