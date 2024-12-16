package com.qt.demo.util;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 类描述：
 *
 * @author : 庆之
 * @version : 1.0
 **/
@Slf4j
public class DateUtil {
    public static final String DATE_PATTERN_WITH_HENGXIAN = "yyyy-MM-dd";
    public static final String DATE_PATTERN = "yyyy/MM/dd";
    private static final String TIME_PATTERN = DATE_PATTERN_WITH_HENGXIAN + " HH:mm:ss";
    private static final String ZHENGXIEXIAN = "/";
    private static final String SPACE = " ";
    private static final String HENG_XIAN = "-";
    /**
     * 锁对象
     */
    private static final Object lockObj = new Object();

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = Maps.newHashMap();


    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    tl = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 将字符串转成时间
     *
     * @param datePattern 格式
     * @param strDate     字符串的时间
     */
    public static Date convertStringToDate(String datePattern, String strDate) {
        Date date;
        // 传入的时间是以 / 分割
        int length = 2;
        if (strDate.split(HENG_XIAN).length < length) {
            strDate = strDate.replace(ZHENGXIEXIAN, HENG_XIAN);
        }
        if (strDate.split(SPACE).length > 1) {
            datePattern = TIME_PATTERN;
        }
        SimpleDateFormat df = getSdf(datePattern);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            log.error("时间[{}]转换异常--->{}", strDate, pe.getMessage());
            return null;
        }
        return (date);
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate;
        if (StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat simpleDateFormat = getSdf(pattern);
            formatDate = simpleDateFormat.format(date);
        } else {
            SimpleDateFormat simpleDateFormat = getSdf("yyyy-MM-dd");
            formatDate = simpleDateFormat.format(date);
        }
        return formatDate;
    }

    public static Date getNextYear(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR,1);
        return c.getTime();
    }
}
