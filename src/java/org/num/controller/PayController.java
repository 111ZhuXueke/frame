package org.num.controller;

import org.num.customAnnotation.RequestUrl;

import javax.servlet.http.HttpSession;

public class PayController {
    @RequestUrl(url="/payCont/zf")
    public String zf(HttpSession session, String string){
        Object obj = session.getAttribute("user");
        return obj != null ? "goPay" : "goIndex";
    }
    @RequestUrl(url="/payCont/backValue")
    public String zf(String parm){
        return "payOk";
    }
}
