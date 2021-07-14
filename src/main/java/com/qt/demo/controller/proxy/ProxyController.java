package com.qt.demo.controller.proxy;

import com.qt.demo.manager.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 11:09
 * @version: V1.0
 */
@RestController
@RequestMapping("proxy")
public class ProxyController {

    @Autowired
    private ProxyManager proxyManager;

    @GetMapping( "java")
    public String javaProxy(){
        return proxyManager.javaProxy();
    }

    @GetMapping("aspect/{type}")
    public String aspectProxy(@PathVariable("type") String type){
        return proxyManager.aspectProxy(type);
    }
}
