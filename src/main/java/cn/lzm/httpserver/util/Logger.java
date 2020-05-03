package cn.lzm.httpserver.util;

import java.text.SimpleDateFormat;

/**
 * @author laosi
 * @version 1.0
 * @since 1.0
 *
 * 打印日志的工具类，会打印三个东西，日志等级，日志打印时间，日志信息
 */
public class Logger {
    /**
     * 工具类构造器私有化，一般不会去new一个构造器
     */
    private Logger(){
    }

    public static void log(String msg){
        System.out.println("[INFO] "+ DateUtil.getCurrentTime()+" "+msg);
    }

}
