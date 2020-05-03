package cn.lzm.httpserver.core;

import cn.lzm.httpserver.util.Logger;
import cn.lzm.oa.servlet.LoginServlet;
import javaxx.servlet.Servlet;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class HandlerRequest implements Runnable{
    /**
     * 服务器套接字
     */
    private Socket client;

    public HandlerRequest(){}

    public HandlerRequest(Socket client){
        this.client = client;
    }

    /**
     * 响应静态页面
     * @param writer
     */
    private void resoponseStaticPage(String uri, Writer writer   ){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/floder"+uri));
            StringBuilder html = new StringBuilder();
            //添加HTTP协议响应信息
            html.append("HTTP/1.1 200 OK\n");
            html.append("contentType:text/html;charset=utf-8\n\n");
            String temp = null;
            while((temp = reader.readLine()) != null){
                html.append(temp);
            }
            //输出信息
            writer.write(html.toString());
        } catch (FileNotFoundException e) {
            //没有找到静态资源，响应404
            StringBuilder notfoud = new StringBuilder();
            notfoud.append("HTTP/1.1 404 NotFound\n");
            notfoud.append("contentType:text/html;charset=utf-8\n\n");
            notfoud.append("<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>商城首页</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1 style=\"color: red;font-size: 100px\" align=\"center\">文件没有找到啊啊啊！！！</h1>\n" +
                    "</body>\n" +
                    "</html>");
            try {
                writer.write(notfoud.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            //打印日志，开启线程了
            Logger.log("Therd Start :"+Thread.currentThread().getName());
            //输入流，获取请求信息
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //输出流，进行响应
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            //获取请求行,就是第一行获取的信息
            String requestLine = reader.readLine(); //GET /llll HTTP/1.1 大概这个样子
            //获取虚拟项目路径
            String requestUri = requestLine.split(" ")[1];
            //判断路径名是否是一个静态页面 ,以.html结尾
            if(requestUri.endsWith(".html")){
                resoponseStaticPage(requestUri,writer);
                //强制刷新
                writer.flush();
            }else{
                //不是静态页面，是请求动态资源，如:
                // /login
                // .login?username=dwdw&password=123456
                String serverletPath = requestUri;
                //判断是否含有?,取到除了参数前的虚拟项目名
                if(requestUri.contains("?")){
                    serverletPath = requestUri.split("[?]")[0];//正则
                }
                //获取应用名
                 // 如 /oa/login  oa应用下的login页面
                String webAppName = serverletPath.split("/")[1];
                //获取项目下的所有url-pattern对应 servlet-class的容器
                Map<String,String> servletMap = WebParser.servletMaps.get(webAppName);

                //从uri中获取请求地址 ，如 /oa/login 中的 /login
                String urlpattern = serverletPath.substring(1+webAppName.length());
                //获取全限定类名
                String classname = servletMap.get(urlpattern);
                if(classname == null){
                    //没有找到相关的类,就是说取到的全限定类名为null,或者配置文件写错了
                    //返回404
                    StringBuilder notfoud = new StringBuilder();
                    notfoud.append("HTTP/1.1 404 NotFound\n");
                    notfoud.append("contentType:text/html;charset=utf-8\n\n");
                    notfoud.append("<html>\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>商城首页</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1 style=\"color: red;font-size: 100px\" align=\"center\">文件没有找到啊啊啊！！！</h1>\n" +
                            "</body>\n" +
                            "</html>");
                    try {
                        writer.write(notfoud.toString());
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    //成功找到类名
                    //反射获取Servlet处理类
                    //创建servlet之前，先从缓存池中寻找，有就返回，没有就创建一个新的
                    Servlet servlet = ServletCache.getServlet(requestUri);
                    if(servlet == null){
                        //第一次创建
                        Class servletClass =  Class.forName(classname);
                        servlet = (Servlet) servletClass.getConstructor(new Class[]{}).newInstance();
                        //记得要存进去
                        ServletCache.setServlet(requestUri,servlet);
                    }
                    System.out.println(servlet);
                    //调用方法
                    writer.write("Http/1.1 200 OK\n");
                    writer.write("contentType:text/html;charset=utf-8\n\n");

                    HttpResponseObject object = new HttpResponseObject();
                    object.setWriter(writer);
                    HttpRequestObject requestObject = new HttpRequestObject(requestUri);
                    servlet.service(requestObject,object);
                }
            }
            System.out.println(requestLine);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
                if (writer != null){
                    writer.close();
                }
                if(client != null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
