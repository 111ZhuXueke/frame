package org.num.util;

/**
 * @author zxk
 * @date 2017-11-26 15:51
 */
public class StringHandle {
    /**
     * 将字符串首字母变成小写
     * @author zxk
     * @date 2017/11/26 17:45
     */
    public static String capitalFirstChar(String string){
        char[] charArray = string.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    /**
     * 将字符串首字母变成大写
     * @author zxk
     * @date 2017/11/26 17:47
     */
    public static String lowercaseFirstChar(String string){
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

}
