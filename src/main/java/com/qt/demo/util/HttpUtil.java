package com.qt.demo.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/24 15:00
 * @version: V1.0
 */
@Slf4j
public class HttpUtil {
    public static <T> T doPostJson(String url, String requestBody,Class<T> className){
        return doPostJson(url,requestBody,null,className);
    }

    public static <T> T doPostJson(String url, String requestBody,Map<String,String> headerMap, Class<T> className){
//
        String result = doPostJson(url, requestBody,headerMap);
        if (StringUtils.isEmpty(result) || !result.startsWith("{")) {
            return null;
        }

        return JSONUtil.toBean(result,className);
    }

    public static String doDelete(String url, String requestBody, Map<String,String> headerMap){


        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach((k,v)->headers.add(k,v));
        }
        HttpMethod method = HttpMethod.DELETE;
        // 以JSON的方式提交
//        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
//        log.info("请求url = {}",url);
//        log.info("请求body = {}",requestBody);
//        log.info("请求res = {}",body);
        return body;
    }

    public static void main(String[] args) {
        String requestBody="{\"query\":\"query QUERY_MANHOURS($groupBy: GroupBy, $orderBy: OrderBy, $timeSeries: TimeSeriesArgs, $timeSeriesWithWorkDays: TimeSeriesArgs, $actualHoursSum: String, $filter: Filter, $columnSource: Source) {\\n  buckets(groupBy: $groupBy, orderBy: $orderBy, filter: $filter) {\\n    ...ColumnBucketFragment\\n  }\\n}\\n\\nfragment TaskSimple on Task {\\n  key\\n  uuid\\n  name\\n  number\\n  project {\\n    uuid\\n  }\\n}\\n\\nfragment ColumnBucketFragment on Bucket {\\n  key\\n  columnField: aggregateTask(source: $columnSource) {\\n    ...TaskSimple\\n  }\\n  actualHours(sum: $actualHoursSum)\\n  actualHoursSeries(timeSeries: $timeSeries) {\\n    times\\n    values\\n  }\\n}\\n\",\"variables\":{\"groupBy\":{\"manhours\":{\"task\":{}}},\"orderBy\":{\"aggregateTask\":{\"createTime\":\"DESC\"}},\"filter\":{\"manhours\":{\"task_notIn\":[null],\"startTime_range\":{\"unit\":\"day\",\"from\":\"2024-10-01\",\"to\":\"2024-10-31\"},\"owner_in\":[\"$currentUser\"]},\"actualHours_notIn\":[0]},\"actualHoursSum\":\"manhours.recordedHour\",\"timeSeries\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"2024-10-01\",\"to\":\"2024-10-31\"},\"timeSeriesWithWorkDays\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"2024-10-01\",\"to\":\"2024-10-31\",\"constant\":800000,\"workdays\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\"]},\"columnSource\":\"task\"}}\n";
        String url="https://pms.sinopharmholding.com/project/api/project/team/9XGSEMCg/items/graphql?t=report-data__workspace_manhour-9XGSEMCg";
        String cookie ="language=zh; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22GMr5xcq1%22%2C%22first_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfbG9naW5faWQiOiJHTXI1eGNxMSIsIiRpZGVudGl0eV9jb29raWVfaWQiOiIxOGViNjFmOTNjMjdhZS0wYWJhNTQyM2FkYWZjYi0xYzUyNTYzNy0yMDA3MDQwLTE4ZWI2MWY5M2MzNDIxIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22GMr5xcq1%22%7D%2C%22%24device_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%7D; route=a901023aae97cb40f51e160dc40621bb; timezone=Asia/Shanghai; APPHUB_WEBUI_SID=87F37E81BE254E3CA4FE5F5334D0BD82; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22GMr5xcq1%22%2C%22first_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfbG9naW5faWQiOiJHTXI1eGNxMSIsIiRpZGVudGl0eV9jb29raWVfaWQiOiIxOGViNjFmOTNjMjdhZS0wYWJhNTQyM2FkYWZjYi0xYzUyNTYzNy0yMDA3MDQwLTE4ZWI2MWY5M2MzNDIxIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22GMr5xcq1%22%7D%2C%22%24device_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%7D; ones-uid=GMr5xcq1; ones-lt=beleBKY8fVJawrJIr7APj0XQ3aqtpdBTjnzU4bVbnknbtzPTUyV40qYFDYUy9NwK; ct=d18ad1b74e9b23d8fef5848efc2de5b4e11cb45349bec2bd2facd094fce2fbe3";
        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",cookie);
//        headers.add("Accept","*/*");
//        headers.add("Accept-Encoding","gzip, deflate, br");
//        headers.add("User-Agent","PostmanRuntime-ApipostRuntime/1.1.0");
//        headers.add("Connection","keep-alive");

        HttpMethod method = HttpMethod.POST;
        // 以JSON的方式提交
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
//        headers.setContentType(MediaType.parseMediaType("application/json"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
        System.out.println("=====");
        System.out.println(body);
    }
    public static String doPostJson(String url, String requestBody, Map<String,String> headerMap){


        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach((k,v)->headers.add(k,v));
        }
        HttpMethod method = HttpMethod.POST;
        // 以JSON的方式提交
//        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
//        log.info("请求url = {}",url);
//        log.info("请求body = {}",requestBody);
//        log.info("请求res = {}",body);
        return body;
    }

    public static String doGetJson(String url){

        String requestBody=null;
        Map<String,String> headerMap = new HashMap<>();

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        if(!CollectionUtils.isEmpty(headerMap)){
            headerMap.forEach((k,v)->headers.add(k,v));
        }
        HttpMethod method = HttpMethod.GET;
        // 以JSON的方式提交
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> responseEntity = client.exchange(url, method, requestEntity, String.class);
        String body = responseEntity.getBody();
        log.info("请求url = {}",url);
        log.info("请求body = {}",requestBody);
        log.info("请求res = {}",body);
        return body;
    }
}
