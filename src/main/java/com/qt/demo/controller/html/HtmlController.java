package com.qt.demo.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/12/04 下午4:01
 * @version: V1.0
 */
@Controller
@RequestMapping("/page")
public class HtmlController {

    @GetMapping("/*.html")
    public String index(HttpServletRequest request) {
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        return "pages/index.html";
    }
}

