package javaxx.servlet;

import java.io.IOException;

/**
 * 假装这是规范的Servlet接口
 */
public interface Servlet {
    void service(ServletRequest request,ServletResponse servletResponse) throws IOException;
}
