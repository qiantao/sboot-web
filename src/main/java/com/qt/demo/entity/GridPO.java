package com.qt.demo.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("member_info")
public class GridPO {

    @Id
    @Field("_id")
    private ObjectId id;
    @Field("member_id")
    private Integer memberId;
}
