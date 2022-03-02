package com.qt.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @ClassName:
 * @Description: 该Bean被创建时需要需要执行的操作
 * @author: QianTao
 * @date: 2021/08/27 15:40
 * @version: V1.0
 */
@Service
@Slf4j
public class SpringBeanInit implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
      log.info("InitializingBean 启动成功");
    }
}
