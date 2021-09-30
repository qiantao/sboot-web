package com.qt.demo.controller.async;

import com.qt.demo.service.async.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/09/16 09:52
 * @version: V1.0
 */
@RestController
@RequestMapping("async")
public class AsyncController {

    private final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("do")
    public void doActive(@RequestParam("num") Integer num){
        for (int i = 0; i < num; i++) {
            asyncService.doActive();
        }
    }
}
