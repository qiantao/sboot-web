package com.qt.demo.handler;

import com.qt.demo.entity.BaseResponse;
import com.qt.demo.entity.ExceptionResponse;
import com.qt.demo.enums.RequestStatusEnum;
import com.qt.demo.exception.MyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/20 11:05
 * @version: V1.0
 */
@ControllerAdvice
public class TscExceptionHandler {

    @ExceptionHandler( value = {Exception.class})
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

}
