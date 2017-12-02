package org.num.tomcatRun.demo01;

/**
 * Run
 * @author : zhuxueke
 * @since : 2017-12-01 16:08
 **/
public class Main {
    public static void main(String[] args){
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }
}
