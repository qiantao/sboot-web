package com.qt.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Comparable, Comparator {
    @Field("name")
    private String name;
    @Field("age")
    private Integer age;
    @Field("sex")
    private String sex;

    private char c;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

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
