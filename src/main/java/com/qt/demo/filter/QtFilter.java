package com.qt.demo.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/23 15:19
 * @version: V1.0
 */
@WebFilter(filterName = "qtFilter",urlPatterns = "/aaa")
@Slf4j
public class QtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.error("unionpay--- qtFilter---init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int port = servletRequest.getServerPort();
        log.error("unionpay--- qtFilter---dofilter,port={}",port);
        
    }

    @Override
    public void destroy() {

    }
}
