package cn.lzm.httpserver.core;

import cn.lzm.httpserver.util.Logger;
import cn.lzm.httpserver.util.ServrParser;
import org.dom4j.DocumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 程序主入口
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class Bootstrap {
    /**
     * 主程序
     * @param args
     */
    public static void main(String[] args) {
        start();
    }

    /**
     * 主程序入口
     */
    public static void start(){
        try{
            Logger.log("HttpServer Start");
            //获取当前时间
            long starttime = System.currentTimeMillis();
            //对所有web.xml进行解析
            WebParser.parser(new String[]{"floder"});
            //获取服务器端口号
            int port = ServrParser.getPort();
            Logger.log("HttpServer Port "+port);
            //创建服务器套接字，并且绑定端口号 8080
            ServerSocket server = new ServerSocket(port);
            //获取结束时间
            long endtime = System.currentTimeMillis();

            Logger.log("HttpServer Started :"+(endtime-starttime));
            while (true){
                Socket client = server.accept();
                new Thread(new HandlerRequest(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            //解析是dom4j 失败
        }
    }
}
