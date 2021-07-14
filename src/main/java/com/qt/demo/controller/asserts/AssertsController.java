package com.qt.demo.controller.asserts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/12 17:43
 * @version: V1.0
 */
@RestController
@RequestMapping("/assert/")
public class AssertsController {

    /**
     * assert 校验需要开启校验参数 启动参数增加 -ea
     * @return
     */
    @GetMapping("test")
    public String assertTest(){
        assert false:"异常信息";
        return "assert 通过";
    }
}
