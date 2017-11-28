package org.num.test;

import org.num.customAnnotation.RequestUrl;
import org.num.customException.RequestUrlNotFound;
import org.num.util.ClassUtil;
import org.num.util.InitAnnotation;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class WtTest {
    public static void main(String[] org) throws URISyntaxException{
        //init("/wtCont/login", "org.num.Controller");
        //init(null, "org.num.controller");
        Map<String,String> map = InitAnnotation.init("org.num.controller");
        for (String item: map.keySet()) {
        	System.out.println(item + "----" + map.get(item));
            System.out.println(item + "----" + map.get(item));
        }
    }
    public static void init(String urlPath, String packageName) throws URISyntaxException{
        String newPackageName = "/" + packageName.replace(".","/");
        //获取指定包下面的所有类名
        String[] classesName = ClassUtil.getPackageUnderClass(newPackageName);
        //存储所有控制类下面的所有注解值
        Map<String, String> annotationInfo = new HashMap<String, String>();
        try{
            for (String item:classesName) {
                String newItem = packageName + "." + item;
                Class clazz = Class.forName(newItem);
                Method[] methods = clazz.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    RequestUrl reqUrl = methods[i].getAnnotation(RequestUrl.class);
                    if(reqUrl != null){
                        if(reqUrl.url().contains(urlPath)){
                           Object obj = methods[i].invoke(clazz.newInstance());
                           if (obj != null){
                               System.out.println((String) obj);
                           }
                        }
                    }
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            try {
                throw  new RequestUrlNotFound();
            } catch (RequestUrlNotFound requestUrlNotFound) {
                requestUrlNotFound.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}