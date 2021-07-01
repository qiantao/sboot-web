package com.qt.demo.manager;

import cn.hutool.json.JSONUtil;
import com.mongodb.client.MongoCollection;
import com.qt.demo.entity.Person;
import com.qt.demo.enums.SexEnums;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:56
 * @version: V1.0
 */
@Component
public class MyManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    public String testMongodb(){
        createDB();
        inserDB(Person.builder().name("张三").age(13).sex(SexEnums.MAN.name()).build());
        inserDB(Person.builder().name("李四").age(14).sex(SexEnums.MAN.name()).build());
        inserDB(Person.builder().name("王五").age(15).sex(SexEnums.MAN.name()).build());


        return "";
    }

    public void query(){
        Query query = new Query();
        query.addCriteria(new Criteria().and("name").is("1"));
        List<Person> people = mongoTemplate.find(query, Person.class);
    }
    public void inserDB(Person person){

        Person insert = mongoTemplate.insert(person);
        System.out.println(JSONUtil.toJsonStr(insert));
    }
    public void createDB(){
        MongoCollection<Document> collection = mongoTemplate.createCollection(Person.class);
        System.out.println(JSONUtil.toJsonStr(collection));
    }

    public void deleteDB(){
//        mongoTemplate.
    }
}
