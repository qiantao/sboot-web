package com.qt.demo.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/23 15:19
 * @version: V1.0
 */
@WebFilter(filterName = "encodeFilter",urlPatterns = "/*")
public class EncodeFilter implements Filter {
    private String code = "utf-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String code = filterConfig.getInitParameter("code");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(code);
        servletResponse.setCharacterEncoding(code);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
