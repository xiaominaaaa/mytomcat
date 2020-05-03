package cn.lzm.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author laosi
 * @version v1.0
 * @since v1.0
 *
 * 日期相关处理类
 */
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS");
    private DateUtil(){}

    /**
     * 获取系统当前时间
     * @return 当前时间装换的字符串
     */
    public static String getCurrentTime(){
        return simpleDateFormat.format(new Date());
    }

}
