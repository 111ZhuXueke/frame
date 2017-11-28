package org.num.util;

import org.num.customAnnotation.Autowired;
import org.num.customAnnotation.JSONScope;
import org.num.customAnnotation.RequestUrl;
import org.num.customException.IncorrectNumberOfParameters;
import org.num.customException.RequestMethodException;
import org.num.customException.URLFormatException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类
 * @author zxk
 */
public class ClassUtil {
    /**
     * 存取Serice接口层的单例
     * @author : zhuxueke
     * @since : 2017/11/27 9:20
     */
    private static Map<String, Object> initSerice = null;

    /**
     * 获取包下的所有类名
     * 包名: /org/num/...
     * @return 类名称(不包含包名)
     */
    public static String[] getPackageUnderClass(String packagename)throws URISyntaxException{
        URL url = ClassUtil.class.getResource(packagename);
        URI uri = url.toURI();
        File file = new File(uri);
        String[] names = file.list();
        for (int i = 0; i < names.length; i++) {
            String under = names[i].substring(0,names[i].indexOf("."));
            names[i] = under;
        }
        return names;
    }

    /**
     * 根据路径执行响应的方法，并接受返回值
     * @param key
     * @param value
     * @return String[] 元素1: 表示返回的页面  元素2: 表示返回的json字符串
     */
    public static String[] getResultByKeyValue(String key,String value,HttpServletRequest req, HttpServletResponse res){
        String[] result = new String[]{null,null};
        Object sericeInstance = null;
        try {
            Class clazz = Class.forName(value);
            Object controllerInstance = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Autowired autowired = fields[i].getAnnotation(Autowired.class);
                if (autowired != null) {
                    sericeInstance = ClassUtil.getInstance(fields[i].getName());
                    if (sericeInstance != null) {
                        fields[i].setAccessible(true);
                        fields[i].set(controllerInstance, sericeInstance);
                    }
                }
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (int j = 0; j < methods.length; j++) {
                if (key.equals(methods[j].getAnnotation(RequestUrl.class).url())) {
                    if (methods[j].getAnnotation(RequestUrl.class).setMethod() == RequestUrl.method.GET && "GET".equals(req.getMethod()) ||
                            methods[j].getAnnotation(RequestUrl.class).setMethod() == RequestUrl.method.POST && "POST".equals(req.getMethod())
                            ) {
                        Map<String, Object> map = getParameterValue(methods[j], req, res);
                        //获取参数值
                        Object obj = clazz.getMethod(methods[j].getName(), Map.class).invoke(controllerInstance, map);
                        if (obj != null) {
                            if(methods[j].getAnnotation(JSONScope.class) != null){
                                result[1] = (String)obj;
                            }else{
                                result[0] = (String)obj;
                            }
                            return result;
                        }
                    } else {
                        throw new RequestMethodException();
                    }
                }
            }
        }catch (RequestMethodException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取方法参数值
     * @return map 用于controller中存取数据
     */
    public static Map<String, Object> getParameterValue(Method method, HttpServletRequest req, HttpServletResponse res){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            if (method.getParameterTypes().length ==1 && method.getParameterTypes().length != 0){
                map.put("request", req);
                map.put("response", res);
                //遍历request的请求参数和值
                Enumeration em = req.getParameterNames();
                while (em.hasMoreElements()){
                    String name = (String)em.nextElement();
                    String value = new String(req.getParameter(name).getBytes("ISO-8859-1"),"UTF-8");
                    Object obj = value;
                    map.put(name,obj);
                }
                String queryString = req.getQueryString();
                if(queryString != null && !"".equals(queryString)){
                    analysisURL(queryString, map);
                }
            }else{
                throw new IncorrectNumberOfParameters();
            }

        }catch (IncorrectNumberOfParameters e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 解析url获取参数
     * @param queryString
     * @param map
     */
    public static void analysisURL(String queryString, Map<String, Object> map){
        try{
            String[] queryList = queryString.split("&");
            for (String item: queryList) {
                String[] strings = item.split("=");
                map.put(new String(strings[0].getBytes("ISO-8859-1"),"UTF-8"), new String(strings[1].getBytes("ISO-8859-1"),"UTF-8"));
            }
        }catch (Exception e){
            try {
                throw new URLFormatException();
            }catch (URLFormatException e1){
                e1.printStackTrace();
            }
        }
    }

    /**
     * 初始化所有serice，使其在整个应用程序中保持单例
     * @author : zhuxueke
     * @since : 2017/11/27 9:19
     */
    public static void InitSerice(String packagePath, String sericePath){
        Map<String, Object> map = new HashMap<String,Object>();
        try{
            String[] controllerArray = getPackageUnderClass(packagePath);
            if(controllerArray.length == 0){
                return;
            }
            String packageName = packagePath.replace("/",".") + ".";
            String newPackagePath = packageName.substring(packageName.indexOf(".") + 1);

            String[] sericeArray = getPackageUnderClass(sericePath);
            if (sericeArray.length == 0){
                return;
            }
            String sericeName = sericePath.replace("/",".") + ".";
            String newericePath = sericeName.substring(sericeName.indexOf(".") + 1);
            for (int i = 0; i < sericeArray.length; i++) {
                sericeArray[i] = StringHandle.capitalFirstChar(sericeArray[i]);
            }
            System.out.println(sericeArray[0]);
            for (int i = 0; i < controllerArray.length; i++) {
                Class clazz = Class.forName(newPackagePath + controllerArray[i]);
                Field[] field = clazz.getDeclaredFields();
                for (int j = 0; j < field.length; j++) {
                    Autowired autowired = field[j].getAnnotation(Autowired.class);
                    if(autowired != null){
                        for (int k = 0; k < sericeArray.length; k++) {
                            if(sericeArray[k].startsWith(field[j].getName())){
                                Class sericeClazz = Class.forName(newericePath + StringHandle.lowercaseFirstChar(sericeArray[k]));
                                field[j].setAccessible(true);
                                //键: controller 中注入的属性  值: 属性对应的子类实例
                                map.put(field[j].getName(),sericeClazz.newInstance());
//                            field[i].set(obj ,sericeClazz.newInstance());
//                            Object ser= clazz.getMethod("getAll").invoke(obj);
//                            System.out.print(ser);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        initSerice = map;
    }

    /**
     * 获取serice的impl实例
     * @author : zhuxueke
     * @since : 2017/11/27 9:56
     */
    public static Object getInstance(String key){
        return initSerice.get(key);
    }
}
