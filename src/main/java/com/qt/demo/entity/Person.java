package com.qt.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class Person {
    @Field("name")
    private String name;
    @Field("age")
    private Integer age;
    @Field("sex")
    private String sex;

}
