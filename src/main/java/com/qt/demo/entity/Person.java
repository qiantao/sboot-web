package com.qt.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:59
 * @version: V1.0
 */
@Document("person")
@Data
@Builder
public class Person implements Comparable, Comparator {
    @Field("name")
    private String name;
    @Field("age")
    private Integer age;
    @Field("sex")
    private String sex;


    @Override
    public int compareTo(Object o) {
        List<String> list =new ArrayList<>();
        Arrays.sort(list.toArray());
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

}
