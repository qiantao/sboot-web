package com.qt.demo.controller.proxy;

import cn.hutool.core.convert.Convert;
import com.qt.demo.entity.GridPO;
import com.qt.demo.entity.Person;
import com.qt.demo.manager.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/change_json")
    public String changeJson(@RequestBody List<Person> str) {
        String s = Convert.toStr(str);
        System.out.println(s);
//        String s ="大大";
        return s;
    }

}
