package cn.lzm.oa.servlet;

import cn.lzm.httpserver.core.HttpRequestObject;
import javaxx.servlet.Servlet;
import javaxx.servlet.ServletRequest;
import javaxx.servlet.ServletResponse;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * 处理登录
 * @version 1.0
 * @author laosi
 * @since 1.0
 */
public class LoginServlet implements Servlet {


    public void service(ServletRequest requestObject, ServletResponse servletResponse) throws IOException {
        BufferedWriter writer = servletResponse.getWriter();
        writer.write("<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>商城首页</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1 style=\"color: red;font-size: 100px\" align=\"center\">登录正在处理！！！！！</h1>\n" +
                "</body>\n" +
                "</html>");
        writer.flush();
    }
}
