package org.num.util;

import org.num.customAnnotation.RequestUrl;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化注解
 * @author zxk
 */
public class InitAnnotation {
    public static Map<String,String> annotationInfos = null;
    public static Map<String,String> getMap(){
        return annotationInfos;
    }
    /**
     * 初始化所有注解
     * @author zxk
     * @date 2017/11/26 17:57
     */
    public static Map<String,String> init(String packageName) throws URISyntaxException {
        //存储所有控制类下面的所有注解值
        Map<String, String> annotationInfo = new HashMap<String, String>();
        String newPackageName = "/" + packageName.replace(".","/");
        //获取指定包下面的所有类名,packageName格式为 org.num....
        String[] classesName = ClassUtil.getPackageUnderClass(newPackageName);
        try{
            for (String item:classesName) {
                String newItem = packageName + "." + item;
                Class clazz = Class.forName(newItem);
                Method[] methods = clazz.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    RequestUrl reqUrl = methods[i].getAnnotation(RequestUrl.class);
                    if(reqUrl != null){
                        annotationInfo.put(reqUrl.url(),clazz.getName());
                    }
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return annotationInfo;
    }
}
