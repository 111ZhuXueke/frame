package org.num.customException;

/**
 * @author : zhuxueke
 * @since : 2017-11-27 14:04
 **/
public class RequestMethodException extends Exception{
    public RequestMethodException(){
        super("request method Incorrect(请求方式不正确)!");
    }
}
