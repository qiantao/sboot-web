package com.qt.demo.service.proxy;

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

    String methodProxy(Class<SubMethodService> subProxyServiceClass);
}
