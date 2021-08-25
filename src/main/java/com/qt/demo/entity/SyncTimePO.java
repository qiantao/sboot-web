package com.qt.demo.entity;

import com.qt.demo.enums.TimeType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("sync_time")
@Data
public class SyncTimePO {

    @Field("time_type")
    private TimeType timeType;
    @Field("busi_key")
    private String busiKey;
    @Field("sync_time")
    private Long SyncTime;
}
