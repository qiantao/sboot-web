package com.qt.demo.annotation.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/08 15:34
 * @version: V1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyAnnotation {
    String value() default "";
}
