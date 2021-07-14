package com.qt.demo.util;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/07/12 15:35
 * @version: V1.0
 */
public class DesenitizedUtil {
    private final static Logger log = LoggerFactory.getLogger(DesenitizedUtil.class);

    /**
     * 姓名脱敏
     *
     * @param fullName
     */
    public static String desensitizedName(String fullName) {
        if (!Strings.isNullOrEmpty(fullName)) {
            String name = StringUtils.right(fullName, 1);
            return StringUtils.leftPad(name, StringUtils.length(fullName), "*");
        }
        return fullName;
    }

    /**
     * 身份证号脱敏
     * @param idNumber
     * @return
     */
    public static String desensitizedIdNumber(String idNumber) {
        if (!Strings.isNullOrEmpty(idNumber)) {
            if (idNumber.length() == 15) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
            }else if (idNumber.length() == 18) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
            }else{
                log.error("身份证号 数据不正确：",idNumber);
                idNumber = "";
            }
        }
        return idNumber;
    }

    /**
     * 手机号进行脱敏
     * @param phoneNumber
     * @return
     */
    public static String desensitizedPhoneNumber(String phoneNumber){
        if (!Strings.isNullOrEmpty(phoneNumber)) {
            if (phoneNumber.length() == 11) {
                phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
            }else{
                log.error("手机号码格式不正确：{}",phoneNumber);
                phoneNumber = "";
            }
        }
        return phoneNumber;
    }
}
