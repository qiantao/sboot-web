package com.qt.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JSONField(serialize = false)
    private ObjectId id;
    @Field("member_id")
    private Integer memberId;
}
