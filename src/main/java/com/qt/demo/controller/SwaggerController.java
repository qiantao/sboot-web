package com.qt.demo.controller;


import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/25 17:37
 * @version: V1.0
 */
/* 类注解 */
@Api(value = "swagger 测试接口")
@RestController
public class SwaggerController {

    /* 方法注解 */
    @ApiOperation(value = "测试接口", notes = "测试接口")
    /* 若需要加额外参数 以此方式添加*/
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "access_token", value = "请求token", required = true) })
    @GetMapping(value="/hello")
    public Object hello( /* 参数注解 */ @ApiParam(value = "desc of param" , required=true ) @RequestParam String name) {
        return "Hello " + name + "!";
    }
}
