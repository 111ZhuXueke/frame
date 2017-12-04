package org.num.tomcatRun.demo02;

/**
 * 服务静态资源请求
 * @author : xp
 * @since : 2017-12-04 12:53
 **/
public class StaticResourceProcessor {

    /**
     *  调用Response对象sendStaticResource方法
     * @author : xp
     * @since : 2017/12/4 12:55
     */
    public void process(Request request, Response response){
        try{
            response.sendStaticResource();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}