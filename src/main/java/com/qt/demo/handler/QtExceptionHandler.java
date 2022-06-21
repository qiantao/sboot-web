package com.qt.demo.handler;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qt.demo.annotation.proxy.NotControllerResponseAdvice;
import com.qt.demo.entity.BasePageResponse;
import com.qt.demo.entity.BaseResponse;
import com.qt.demo.entity.ExceptionResponse;
import com.qt.demo.enums.RequestStatusEnum;
import com.qt.demo.exception.MyException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/20 11:05
 * @version: V1.0
 */
@RestControllerAdvice
public class QtExceptionHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(value = {Exception.class})
    @NotControllerResponseAdvice
    public ResponseEntity<BaseResponse<ExceptionResponse>> exceptionHandler(Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        if(e instanceof MyException){
            MyException myException = (MyException) e;
            exceptionResponse.setMsg(myException.getMessage());
            exceptionResponse.setErrorInfo(((MyException) e).getErrorInfo());
            return ResponseEntity.ok(BaseResponse.result(myException.getRequestStatusEnum(),exceptionResponse));
        }


        exceptionResponse.setMsg(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.ok(BaseResponse.result(RequestStatusEnum.ERROR,exceptionResponse));
    }

    /**
     * response是否需要封装成统一返回对象
     * @param methodParameter the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        // response是BaseResponse类型，或者注释了NotControllerResponseAdvice都不进行包装
        return !(methodParameter.getParameterType().isAssignableFrom(BaseResponse.class)
                || methodParameter.hasMethodAnnotation(NotControllerResponseAdvice.class));
    }

    /**
     * response body 如何封装
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
                return JSONUtil.toJsonStr(BaseResponse.result(body));
        }
        // 否则直接包装成ResultVo返回
        return BaseResponse.result(body);
    }
}
