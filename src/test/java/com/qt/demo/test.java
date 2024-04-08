package com.qt.demo;

import cn.hutool.json.JSONObject;

/**
 * @Author MuYang
 * @Date 2022/11/24 10:32
 * @version: V1.0
 */
public class test {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("a",null);
        jsonObject.set("b",null);
        jsonObject.set("c",null);
        jsonObject.set("d","d");

        System.out.println(jsonObject.keySet());

    }

}
