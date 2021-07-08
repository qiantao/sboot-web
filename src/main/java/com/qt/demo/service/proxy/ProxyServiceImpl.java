package com.qt.demo.service.proxy;

import com.qt.demo.annotation.proxy.ProxyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 14:07
 * @version: V1.0
 */
@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService{

    @Override
    public String testProxy() {
        log.info("proxy-----ing--------");
        return "success";
    }


    @Override
    @ProxyAnnotation
    public String testAnnotation(String type){
        log.info("proxy---ing---annotation  type = {}",type);
        return "annotation-success";
    }
}
