package org.num.customServlet;

import org.num.customException.RequestUrlNotFound;
import org.num.util.ClassUtil;
import org.num.util.InitAnnotation;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class InitServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取项目绝对路径
        Object obj = req.getSession().getAttribute("basePath");
        if(obj == null){
            String path = req.getContextPath();
            String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path
                    + "/";
            obj = (Object)basePath;
            req.getSession().setAttribute("basePath", basePath);
        }
        //访问路径，不包含参数
        String urlPath = req.getServletPath();
        //获取所有参数
        String parms = req.getQueryString();
        //获取所有被RequestUrl注解的路径
        Map<String,String> map = InitAnnotation.getMap();
        try{
            if(map.get(urlPath) != null){
                String[] result = ClassUtil.getResultByKeyValue(urlPath,map.get(urlPath),req,resp);
                if(result[1] != null){
                    resp.getWriter().print(result[1]);
                }else{
                    resp.sendRedirect((String)obj + result[0] + ".jsp");
                }
            }else{
                throw new RequestUrlNotFound(urlPath);
            }
        }catch (RequestUrlNotFound e){
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try{
            InitAnnotation.annotationInfos = InitAnnotation.init("org.num.controller");
            ClassUtil.InitSerice("/org/num/controller","/org/num/serice/impl");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        super.init(config);
    }
}