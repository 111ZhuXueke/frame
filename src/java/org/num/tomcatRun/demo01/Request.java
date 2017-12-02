package org.num.tomcatRun.demo01;

import java.io.InputStream;

/**
 * HttpServletRequest
 * @author : zhuxueke
 * @since : 2017-12-01 15:24
 **/
public class Request {
    private InputStream inputStream;
    private String servletPath;

    public Request(InputStream inputStream){
        this.inputStream = inputStream;
    }

    /**
     * 用于将inputStream转换成字节
     * @author : zhuxueke
     * @since : 2017/12/1 15:29
     */
    public void parse(){
        StringBuffer stringBuffer = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try{
            i = inputStream.read(buffer);
        }catch (Exception e){
            i = -1;
            e.printStackTrace();
        }
        for (int j = 0; j < i; j++) {
            stringBuffer.append((char)buffer[j]);
        }
        String requestString = stringBuffer.toString();
        servletPath = parseUri(requestString);
    }


    private String parseUri(String stringBuffer){
        int index1,index2;
        index1 = stringBuffer.indexOf(' ');
        if(index1 != -1){
            index2 = stringBuffer.indexOf(' ', index1 + 1);
            if(index2 > index1) return stringBuffer.substring(index1 + 1,index2);
        }
        return null;
    }

    /**
     * 获取uri 访问路径==》 request.getServletPath
     * @author : zhuxueke
     * @since : 2017/12/1 15:32
     */
    public String getServletPath(){
        return this.servletPath;
    }


}
