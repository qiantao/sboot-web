package com.qt.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //在请求头中设置userInfo信息
        Map<String,String> headerMap = new HashMap<>();
//        headerMap.put(HeadConst.USER_INFO,user.getUserId().toString());
        modifyHeaders(headerMap,request);
//        request.setAttribute(HeadConst.USER_INFO,user.getUserId());

//        request.getRequestDispatcher(path).forward(request, response);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) throws Exception {

    }

    /**
     * 修改请求头信息
     * @param headerses
     * @param request
     */
    private void modifyHeaders(Map<String, String> headerses, HttpServletRequest request) {
        if (headerses == null || headerses.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            for (Map.Entry<String, String> entry : headerses.entrySet()) {
                o2.removeHeader(entry.getKey());
                o2.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
