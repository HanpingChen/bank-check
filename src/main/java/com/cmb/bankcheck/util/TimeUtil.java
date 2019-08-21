package com.cmb.bankcheck.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-21
 * Time:10:06
 * 时间转化工具类
 */
public class TimeUtil {

    public static String convertDateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
