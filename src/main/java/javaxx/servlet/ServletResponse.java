package javaxx.servlet;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * 假装这是规范的Servlet
 */
public interface ServletResponse {
    BufferedWriter getWriter();
}
