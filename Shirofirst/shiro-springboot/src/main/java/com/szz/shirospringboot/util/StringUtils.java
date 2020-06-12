package com.szz.shirospringboot.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    private StringUtils() {
    }

    public static String dateToStr(Date date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (date == null) ? "" : sdf.format(date);
    }

    /**
     * 验证对象是否为空或NULl
     *
     * @param str 验证对象
     * @return 处理结果 空/Null:true,否则:false
     */
    public static boolean isEmptyOrNull(Object str) {
        if ("".equals(str) || null == str) {
            return true;
        }
        return false;
    }
    
    /**
     * 验证对象不为空也不为NULL
     *
     * @param str 验证对象
     * @return 处理结果 空/Null:true,否则:false
     */
    public static boolean isNotEmptyOrNull(Object str) {
        if (!"".equals(str) && null != str) {
            return true;
        }
        return false;
    }
}
