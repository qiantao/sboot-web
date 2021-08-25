package com.qt.demo.common.redis;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/25 14:35
 * @version: V1.0
 */
public interface RedisCache {

    String get(String key);

    Boolean set(String key,String value,Long seconds);

    Boolean remove(String key);
}
