package com.qt.demo.controller.ones;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qt.demo.consts.OnesConst;
import com.qt.demo.enums.RequestStatusEnum;
import com.qt.demo.exception.MyException;
import com.qt.demo.util.DateUtil;
import com.qt.demo.util.HttpUtil;
import com.qt.demo.util.MDLExcelExportUtil;
import com.qt.demo.util.OnesWorkFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/08/28 上午9:16
 * @version: V1.0
 */
@RestController
@Slf4j
@RequestMapping("/ones")
public class OnesController {

    @GetMapping("/export/{year}/{month}")
    public String export(HttpServletResponse response, HttpServletRequest request, @PathVariable String year, @PathVariable String month) {
        String cookie = OnesConst.cookie;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            cookie = "";
            for (Cookie c : cookies) {
                cookie = cookie + c.getName()+"="+c.getValue() + "; ";
            }
            cookie = cookie.substring(0, cookie.length() - 1);
        }
        List<OnesWorkFile> export = export(year, month,cookie);
        if(export==null) throw new MyException(RequestStatusEnum.TOKEN_NOT_FOUND);
        MDLExcelExportUtil.exportExcel(export, OnesWorkFile.class,"ITO外包人员工作报告-返利"+year+"年"+month+"月","ITO日报-钱涛-软通20"+year+month+"月",response);
        return "success";

    }

    public  List<OnesWorkFile> export(String year, String month,String cookie) {

        String beginDate = String.format("20%s-%s-01", year, month);
        String endDate = String.format("20%s-%s-30",year,month);
        String url = OnesConst.exportUrl;
        Map<String,String> head = new HashMap<>();
        head.put("cookie",cookie);
        String requestBody = String.format(OnesConst.requestBody,beginDate,endDate,beginDate,endDate,beginDate,endDate);
        String s = HttpUtil.doPostJson(url, requestBody, head);
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
                    Date date1 = DateUtil.convertStringToDate(DateUtil.DATE_PATTERN_WITH_HENGXIAN, date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                        continue;
                    }
                    int numWeek = c.get(Calendar.WEEK_OF_MONTH);
                    OnesWorkFile f = new OnesWorkFile();
                    f.setDate(date);
                    f.setWeek("第"+numWeek+"周");
//                    f.setProblemDesc(content);
//                    f.setDesc("");
                    exportList.add(f);
                }
            }
        }
        for (OnesWorkFile onesWorkFile : exportList) {
            onesWorkFile.setWorkContent(dateTaskMap.get(onesWorkFile.getDate()));
        }
        return exportList;
    }
}
