package cn.lzm.httpserver.core;

import javaxx.servlet.ServletResponse;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * 自己定义的响应对象
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class HttpResponseObject implements ServletResponse {
    private BufferedWriter writer;

    public void setWriter(BufferedWriter writer){
        this.writer = writer;
    }

    public BufferedWriter getWriter() {
        return writer;
    }
}
