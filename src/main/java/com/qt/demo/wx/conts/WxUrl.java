package com.qt.demo.wx.conts;

/**
 * @ClassName:
 * @Description:
 * @author: MuYang
 * @date: 2021/09/24 16:19
 * @version: V1.0
 */
public interface WxUrl {
    String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
}
