package com.qt.demo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qt.demo.util.ExcelExportMyStyler;
import com.qt.demo.util.OnesWorkFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: XieZengWei
 * @DATE: 2023/7/10 10:59
 */
@Slf4j
@RestController
@RequestMapping("/test/")
public class TestController {

    static String exportUrl = "https://pms.sinopharmholding.com/project/api/project/team/9XGSEMCg/items/graphql?t=report-data__workspace_manhour-9XGSEMCg";


    static String requestBodyStr = "{\"query\":\"query QUERY_MANHOURS($groupBy: GroupBy, $orderBy: OrderBy, $timeSeries: TimeSeriesArgs, $timeSeriesWithWorkDays: TimeSeriesArgs, $actualHoursSum: String, $filter: Filter, $columnSource: Source) {\\n  buckets(groupBy: $groupBy, orderBy: $orderBy, filter: $filter) {\\n    ...ColumnBucketFragment\\n  }\\n}\\n\\nfragment TaskSimple on Task {\\n  key\\n  uuid\\n  name\\n  number\\n  project {\\n    uuid\\n  }\\n}\\n\\nfragment ColumnBucketFragment on Bucket {\\n  key\\n  columnField: aggregateTask(source: $columnSource) {\\n    ...TaskSimple\\n  }\\n  actualHours(sum: $actualHoursSum)\\n  actualHoursSeries(timeSeries: $timeSeries) {\\n    times\\n    values\\n  }\\n}\\n\",\"variables\":{\"groupBy\":{\"manhours\":{\"task\":{}}},\"orderBy\":{\"aggregateTask\":{\"createTime\":\"DESC\"}},\"filter\":{\"manhours\":{\"task_notIn\":[null],\"startTime_range\":{\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\"},\"owner_in\":[\"$currentUser\"]},\"actualHours_notIn\":[0]},\"actualHoursSum\":\"manhours.recordedHour\",\"timeSeries\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\"},\"timeSeriesWithWorkDays\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\",\"constant\":800000,\"workdays\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\"]},\"columnSource\":\"task\"}}";

    @GetMapping("/export/{year}/{month}/{name}")
    public String onesExport(HttpServletResponse response, HttpServletRequest request, @PathVariable String year, @PathVariable String month
            , @PathVariable String name) {
        String cookie = "";
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies) {
                cookie = cookie + c.getName()+"="+c.getValue() + "; ";
            }
            cookie = cookie.substring(0, cookie.length() - 1);
        }
        List<OnesWorkFile> export = export(year, month,cookie);
        if(export==null) return "导出失败";
        exportExcel(export, OnesWorkFile.class,"ITO外包人员工作报告-返利"+year+"年"+month+"月","ITO日报-"+name+"-软通20"+year+month+"月",response);
        return "success";

    }

    public  List<OnesWorkFile> export(String year, String month,String cookie) {
        YearMonth yearMonth = YearMonth.of(Integer.parseInt("20"+year), Integer.parseInt(month));
        int daysInMonth = yearMonth.lengthOfMonth();
        System.out.println("该月的天数是：" + daysInMonth);

        String beginDate = String.format("20%s-%s-01", year, month);
        String endDate = String.format("20%s-%s-"+daysInMonth,year,month);
        String url = exportUrl;
        Map<String,String> head = new HashMap<>();
        head.put("cookie",cookie);
        String requestBody = String.format(requestBodyStr,beginDate,endDate,beginDate,endDate,beginDate,endDate);
        String s = doPostJson(url, requestBody, head);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        if(jsonObject.get("code") != null){
            return null;
        }
        JSONArray buckets = jsonObject.getJSONObject("data").getJSONArray("buckets");
        if(buckets == null || buckets.size() < 1) System.out.println("无数据");

        List<OnesWorkFile> exportList = new ArrayList<>();
        Map<String,String> dateTaskMap = new HashMap<>();
        Map<String, AtomicInteger> countMap = new HashMap<>();
        for (int i = 0; i < buckets.size(); i++) {
            JSONObject item = buckets.getJSONObject(i);
            //日期返回和使用时间
            JSONObject dateTime = item.getJSONObject("actualHoursSeries");
            JSONArray dateUseTime = dateTime.getJSONArray("values");
            JSONArray dateScope = dateTime.getJSONArray("times");

            //任务名称
            JSONObject columnField = item.getJSONObject("columnField");
            String taskName = columnField.getStr("name");
//            String taskName = "#"+columnField.getInt("number")+":"+columnField.getStr("name");
            for (int j = 0; j < dateScope.size(); j++) {
                String date = dateScope.getStr(j);
                Integer useTime = dateUseTime.getInt(j);
                String content = dateTaskMap.get(date);
                if(StringUtils.isBlank(content)) {
                    countMap.put(date,useTime > 0 ? new AtomicInteger(1) : new AtomicInteger(0));
                    dateTaskMap.put(date,useTime > 0 ? countMap.get(date).getAndAdd(1)+":"+taskName:"");
                }else {
                    dateTaskMap.put(date,useTime > 0 ? content + "\n\t"+countMap.get(date).getAndAdd(1) +":"+ taskName : content);
                }
                if(content == null) {
                    Date date1 = convertStringToDate("yyyy-MM-dd", date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//                    if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
//                        continue;
//                    }
                    int numWeek = c.get(Calendar.WEEK_OF_MONTH);
                    OnesWorkFile f = new OnesWorkFile();
                    f.setDate(date);
                    f.setWeek("第"+numWeek+"周");
                    exportList.add(f);
                }
            }
        }
        Iterator<OnesWorkFile> it = exportList.iterator();
        while (it.hasNext()) {
            OnesWorkFile onesWorkFile = it.next();
            String s1 = dateTaskMap.get(onesWorkFile.getDate());
            if(StringUtils.isBlank(s1)){
                it.remove();
            }else{
                onesWorkFile.setWorkContent(s1);
            }
        }
//        for (OnesWorkFile onesWorkFile : exportList) {
//            String s1 =
//
//            onesWorkFile.setWorkContent();
//        }
        return exportList;
    }

    public static Date convertStringToDate(String datePattern, String strDate) {
        Date date;
        // 传入的时间是以 / 分割
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            log.error("时间[{}]转换异常--->{}", strDate, pe.getMessage());
            return null;
        }
        return date;
    }


    public static void exportExcel(Collection<?> listData, Class<?> pojoClass, String headTitle, String sheetName, HttpServletResponse response) {

        String title = headTitle;
//        ExportParams params = new ExportParams(title, sheetName);
        ExportParams params = new ExportParams(null, sheetName);
        params.setHeight((short) 30);
        params.setStyle(ExcelExportMyStyler.class);
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, pojoClass, listData);
            String fileName = headTitle;
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.reset();
            response.setContentType("application/vnd.ms-excel;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName + ".xls");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            ServletOutputStream out=response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
