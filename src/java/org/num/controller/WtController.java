package org.num.controller;

import com.alibaba.fastjson.JSON;
import org.num.customAnnotation.Autowired;
import org.num.customAnnotation.JSONScope;
import org.num.customAnnotation.RequestUrl;
import org.num.entity.Wt;
import org.num.serice.IWtSerice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class WtController {

    @Autowired
    private IWtSerice wtSerice;

    /**
     * 登录
     * @author zxk
     * @date 2017/11/26 15:49
     */
    @RequestUrl(url="/wtCont/login")
    public String login(Map<String, Object> map){
        return "index";
    }

    /**
     * 列表
     * @author zxk
     * @date 2017/11/26 15:49
     */
    @RequestUrl(url="/wtCont/index",setMethod = RequestUrl.method.GET)
    @JSONScope
    public String getAll(Map<String, Object> map){
        String json = wtSerice.findAll();
        return json;
    }

}
