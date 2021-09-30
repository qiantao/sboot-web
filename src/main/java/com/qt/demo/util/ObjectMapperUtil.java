package com.qt.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qt.demo.entity.Person;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/27 10:24
 * @version: V1.0
 */
public class ObjectMapperUtil {
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Person person = mapper.readValue("{\"lk\":\"1\"}", Person.class);
        System.out.println(person);
    }
}
