package org.num.tomcatRun.demo02;

import org.num.tomcatRun.demo01.Request;
import org.num.tomcatRun.demo01.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 主线程
 * @author : zhuxueke
 * @since : 2017-12-01 15:50
 **/
public class HttpServer {
    // 设置静态资源路径
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    // 停止命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    // 表示是否停止tomcat的运行
    private boolean shutdown = false;
    private int port = 8082;
    public void await(){
        ServerSocket serverSocket = null;
        try{
            // 启动socket服务，监听本地端口
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        }catch (IOException e){
            e.printStackTrace();
        }
        while (!shutdown){
            Socket socket = null;
            // 表示request
            InputStream inputStream = null;
            // 表示response
            OutputStream outputStream = null;
            try{
                // 监听新的连接
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                // 将流转成字符串并设置servletPath
                Request request = new Request(inputStream);
                request.parse();
                //定向资源
                Response response = new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                //判断访问的路径是否包含servlet关键字
                if(request.getServletPath().startsWith("/servlet/")){

                }else{

                }
                socket.close();
                // 判断输入的路径是否是关闭命令
                shutdown = request.getServletPath().equals(SHUTDOWN_COMMAND);
            }catch (Exception e){
                e.printStackTrace();
                // 出现异常保证程序运行
                continue;
            }
        }
    }
}