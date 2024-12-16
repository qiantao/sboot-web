package com.qt.demo.service.proxy;

import com.qt.demo.annotation.proxy.MehodAnnotation;
import org.springframework.stereotype.Service;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/04/10 上午11:19
 * @version: V1.0
 */
@Service
public class SubMethodService extends MethodService{

    @MehodAnnotation
    public boolean doAction(String myName){
        System.out.println("SubMethodService--doAction");
        return true;
    }
}
