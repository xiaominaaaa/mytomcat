package cn.lzm.oa.servlet;

import javaxx.servlet.Servlet;
import javaxx.servlet.ServletRequest;
import javaxx.servlet.ServletResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 处理注册
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class LoginSaveServlet  implements Servlet {

    public void service(ServletRequest request, ServletResponse servletResponse) throws IOException {
        BufferedWriter writer = servletResponse.getWriter();
        writer.write("<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>商城首页</title>\n" +
                "</head>\n" +
                "<body>\n");
        writer.write("姓名:"+URLDecoder.decode(request.getParameter("username"),"utf-8") +"<br>");
        writer.write("性别"+URLDecoder.decode(request.getParameter("sex"),"utf-8")+"<br>");
        writer.write("爱好"+URLDecoder.decode(Arrays.toString(request.getParameters("like")),"utf-8"));
        writer.write("</body>\n" +
                "</html>");
        writer.flush();
    }
}
