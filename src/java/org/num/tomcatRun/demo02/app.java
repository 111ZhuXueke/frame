package org.num.tomcatRun.demo02;

/**
 * @author : xp
 * @since : 2017-12-04 16:57
 **/
public class app {
    public static void main(String[] arg){
        HttpServer httpServer = new HttpServer();
        httpServer.await();
        // T 和 ? 的区别
//        int num = 1;
//        String string = "Hello T";
//        System.out.println(new Query().getObjName(int.class));
//        System.out.println(new Query1<Integer>().getObjName(num));
//        System.out.println(new Query1<Query>().getObjName(new Query()));
    }
}
