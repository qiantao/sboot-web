package com.qt.demo.scheduler;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.qt.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/06 16:06
 * @version: V1.0
 */
@Component
@Slf4j
public class TestScheduler {

    static List<String> paramList = new ArrayList<>();
    static {
//        paramList.add("{\"userName\":\"li.wang-os@perfma.com\",\"password\":\"Wl20161001`\"}");
//        paramList.add("{\"userName\":\"tao.qian-os@perfma.com\",\"password\":\"Qiantao2114@\"}");
    }

//    public static void main(String[] args) {
//        String s = "{\"userName\":\"tao.qian-os@perfma.com\",\"password\":\"Qiantao2114@\"}";
//        String token = getToken(s);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int day = 8;
//        while (day>0) {
//            String format = sdf.format(new Date(c.getTimeInMillis()));
//            int i1 = c.get(Calendar.DAY_OF_WEEK);
//            if (i1 == 1 || i1 == 7) {
//                c.add(Calendar.DAY_OF_WEEK,-1);
//                continue;
//            }
//            day = list(token,format,day);
//            c.add(Calendar.DAY_OF_WEEK,-1);
//        }
//    }

    public static int list(String token,String date,int day){
        String url = "http://csms.perfma-inc.com:12306/api/csms/worktime/list";
        Map<String,String> headMap = new HashMap<>();
        headMap.put("token",token);
        String requestParam = "{\"startTime\":\""+date+"T07:35:41.643Z\",\"endTime\":\""+date+"T07:35:41.643Z\",\"nickName\":\"牧羊\"}";
        HashMap hashMap = HttpUtil.doPostJson(url, requestParam,headMap, HashMap.class);
        log.info(JSONUtil.toJsonStr(hashMap));
        JSONArray object = (JSONArray)hashMap.get("object");
        if(object.size()==1){
            JSONObject o = object.getJSONObject(0);
            if(Objects.equals("金拱门（中国）有限公司",o.get("customerName")+"")&&
               Objects.equals(o.getDouble("worktime"),8.0D)){
                Long worktimeId = o.getLong("worktimeId");
                delete(worktimeId,token,date);
                insert(token,date);
                return day - 8;
            }
        }

        return day;
    }

    public static void delete(Long worktimeId,String token,String date){
        String url = "http://csms.perfma-inc.com:12306/api/csms/worktime/delete";
        Map<String,String> headMap = new HashMap<>();
        headMap.put("token",token);
        String requestParam = "{\"worktimeId\":"+worktimeId+",\"worktimeDate\":\""+date+"\"}";
        HttpUtil.doDelete(url, requestParam, headMap);
    }
    public static void insert(String token,String date){
        String url = "http://csms.perfma-inc.com:12306/api/csms/worktime/insert";
        Map<String,String> headMap = new HashMap<>();
        headMap.put("token",token);
        String requestParam = "{\"customerId\":15,\"projectId\":261,\"workContent\":5,\"worktime\":8,\"remark\":\"\",\"worktimeDate\":\""+date+"T08:01:11.268Z\",\"phase\":3,\"worktimeType\":1}";
        HashMap hashMap = HttpUtil.doPostJson(url, requestParam,headMap, HashMap.class);
    }

    @Scheduled(cron = "0 0 19 1/1 * ?")
    public void addTime(){
        try {
            for (String s : paramList) {
                String token = getToken(s);
                if(StringUtils.isBlank(token) || token.contains("null")){
                    log.error("token 为空");
                    return;
                }
                Map hashMap = add(token);
                if("000000".equals(hashMap.get("code")+"")){
                    log.info("请求成功");
                }else if("200001".equals(hashMap.get("code")+"")){
                    token = getToken(s);
                    add(token);
                }else if("1".equals(hashMap.get("code")+"")) {
                    log.error("节假日跳过");
                }
            }
        }catch (Exception e){
            log.error("增加工时失败");
        }
    }


    private Map add(String token){
        String url = "http://csms.perfma-inc.com:12306/api/csms/worktime/insert";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int i1 = c.get(Calendar.DAY_OF_WEEK);
        if(i1 == 1||i1 ==7){
            HashMap<String, String> objectObjectHashMap = Maps.newHashMap();
            objectObjectHashMap.put("code","1");
            return objectObjectHashMap;
        }

        String requestParam = "{\"workContent\":12,\"worktime\":8,\"remark\":\"\",\"worktimeDate\":\""+format+"T08:26:03.698Z\",\"worktimeType\":2}";
        Map<String,String> headMap = new HashMap<>();
//        headMap.put("token","eyJhbGciOiJIUzI1NiJ9.eyJwYXNzV29yZCI6IlFpYW50YW8yMTE0QCIsInVzZXJOYW1lIjoidGFvLnFpYW4tb3NAcGVyZm1hLmNvbSIsInVzZXJJZCI6NDUwLCJuaWNrTmFtZSI6IueJp-e-iiIsImlhdCI6MTY2MDEyMDE4OCwiZXhwIjoxNjYxNDE2MTg4fQ.lMmQKAxQJMF6G9W4BC7Xi8IbCFrQ9OwbD7Km1ACgntI");
        headMap.put("token",token);
        HashMap hashMap = HttpUtil.doPostJson(url, requestParam,headMap, HashMap.class);
        log.info(JSONUtil.toJsonStr(hashMap));
        return hashMap;
    }


//    @Scheduled(fixedDelay = 1000*60*60)
    public static String getToken(String param){
        try {
            String url = "http://csms.perfma-inc.com:12306/api/csms/login";
//            String requestParam = "{\"userName\":\"li.wang-os@perfma.com\",\"password\":\"Wl20161001`\"}";
//            String requestParam = "{\"userName\":\"tao.qian-os@perfma.com\",\"password\":\"Qiantao2114@\"}";
            HashMap hashMap = HttpUtil.doPostJson(url, param, HashMap.class);
            if("000000".equals(hashMap.get("code")+"")){
                log.info("请求成功");

                String object = JSONUtil.toJsonStr(hashMap.get("object"));
                Map map = JSONUtil.toBean(object, Map.class);
                String token = map.get("token")+"";
                log.info(token);
                return token;
            }
        }catch (Exception e){

        }

        return "";

    }
}
