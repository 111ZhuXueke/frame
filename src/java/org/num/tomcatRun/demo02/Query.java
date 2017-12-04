package org.num.tomcatRun.demo02;

/**
 * @author : xp
 * @since : 2017-12-04 17:12
 **/
public class Query {
    public String getObjName(Class<?> str){
        return str.getName();
    }
}

class Query1<T>{
    public String getObjName(T str){
        return str.getClass().getName();
    }
}