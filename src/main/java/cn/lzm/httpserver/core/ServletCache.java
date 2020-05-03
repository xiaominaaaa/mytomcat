package cn.lzm.httpserver.core;

import javaxx.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Servlet对象缓存池
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class ServletCache {
    /**
     * 存放servlet的容器
     */
    private static Map<String, Servlet> servletMaps = new HashMap<String, Servlet>();

    /**
     * 返回servlet
     * @param url
     * @return
     */
    public static Servlet getServlet(String url){
        return servletMaps.get(url);
    }

    /**
     * 设置servlet
     * @param uri
     * @param servlet
     */
    public static void setServlet(String uri,Servlet servlet){
        servletMaps.put(uri,servlet);
    }
}
