package com.qt.demo.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.qt.demo.entity.GridPO;
import com.qt.demo.entity.Person;
import com.qt.demo.entity.SyncTimePO;
import com.qt.demo.enums.TimeType;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
//        createDB();
//        inserDB(Person.builder().name("张三").age(13).sex(SexEnums.MAN.name()).build());
//        inserDB(Person.builder().name("李四").age(14).sex(SexEnums.MAN.name()).build());
//        inserDB(Person.builder().name("王五").age(15).sex(SexEnums.MAN.name()).build());
        String busiKey = "busiKey";
        SyncTimePO one = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("time_type").is(TimeType.EVENT))
                .addCriteria(Criteria.where("busi_key").is(busiKey)), SyncTimePO.class);
        if(one == null){
            one = new SyncTimePO();
            one.setTimeType(TimeType.EVENT);
            one.setSyncTime(0L);
            one.setBusiKey(busiKey);
            mongoTemplate.insert(one);
        }
        return JSONUtil.toJsonStr(one);
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
