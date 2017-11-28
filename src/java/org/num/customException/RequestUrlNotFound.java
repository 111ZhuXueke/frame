package org.num.customException;

/**
 * 路径找不到
 * @author zxk
 */
public class RequestUrlNotFound extends Exception {
    public RequestUrlNotFound(){
        super("The access path was not found（访问的路径找不到）");
    }
    public RequestUrlNotFound(String path){
        super("The " + path + " was not found（访问的路径找不到）");
    }
}
