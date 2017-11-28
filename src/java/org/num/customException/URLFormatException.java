package org.num.customException;

/**
 * 路径问题
 * @author zxk
 */
public class URLFormatException extends Exception {
    public URLFormatException(){
        super("The request path is incorrect（请求路径不正确）");
    }
}
