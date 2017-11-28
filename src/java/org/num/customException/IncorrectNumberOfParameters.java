package org.num.customException;

/**
 * 参数数量不正确
 * @author zxk
 */
public class IncorrectNumberOfParameters extends Exception{
    public IncorrectNumberOfParameters(){
        super("incorrect number of parameters（参数个数不正确）");
    }
}
