package com.qt.demo.common.event;

import com.qt.demo.enums.RequestStatusEnum;
import com.qt.demo.exception.MyException;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class EventBeanFactory {
    public static final String FirstEventName = "event1";
    public static final String SecondEventName = "event2";

    private static Map<String, EventBus> beanMap = new HashMap();
    static {
        beanMap.put(FirstEventName,new EventBus());
        beanMap.put(SecondEventName,new EventBus());
    }

    public static EventBus getEventBus(String eventName) {
        if(beanMap.containsKey(eventName)){
            return beanMap.get(eventName);
        }
        throw new MyException(RequestStatusEnum.USER_NOT_EXIT)
                .put("className","EventBeanFactory")
                .put("method","getEventBus")
                .put("param",eventName);
    }

}
