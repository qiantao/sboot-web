package com.qt.demo.controller.asserts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("msg")
    public String msg(){
        Map<String,Object> map = new HashMap();
        map.put("\"errCode\"","\"200\"");
        map.put("\"msg\"","\"预约日期选择成功！\"");
        map.put("\"dd\"","false");
        map.put("\"ff\"","\"fdsakjhf2%7……*\"");

        return map.toString();

    }

}
