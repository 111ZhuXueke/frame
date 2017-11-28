package org.num.customThread;

public class Demo1 {
    public static void main(String[] arg){
        Thread rh = new Thread("now");
        Thread.yield();
        rh.run();
        rh.start();
        System.out.println(2);
    }
}
