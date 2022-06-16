package com.qt.demo.base;

import cn.hutool.json.JSONUtil;
import com.qt.demo.entity.Person;

import java.util.Date;
import java.util.Optional;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/12 17:29
 * @version: V1.0
 */
public class Test {
    public static void main(String[] args) {
        Person p = new Person();
        p.setAge(20);
        p = null;
        Optional<Person> p1 = Optional.ofNullable(p);
        System.out.println(p1.isPresent());
        System.out.println(JSONUtil.toJsonStr(p1.orElse(new Person())));
        Person person1 = p1.orElseGet(() -> new Person());
        System.out.println(p1.orElseGet(() ->new Person()));
        if(p1.isPresent()) {
            Person person = p1.get();
            System.out.println(JSONUtil.toJsonStr(person));
        }

    }
}
