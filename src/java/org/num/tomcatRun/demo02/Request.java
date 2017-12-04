package org.num.tomcatRun.demo02;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * HttpServletRequest
 * @author : xp
 * @since : 2017-12-01 15:24
 **/
public class Request implements ServletRequest{
    private InputStream inputStream;
    private String servletPath;

    public Request(InputStream inputStream){
        this.inputStream = inputStream;
    }

    /**
     * 用于将inputStream转换成字节
     * @author : xp
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
     * @author : xp
     * @since : 2017/12/1 15:32
     */
    public String getServletPath(){
        return this.servletPath;
    }

    public Object getAttribute(String name) {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return 0;
    }

    public long getContentLengthLong() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String name) {
        return null;
    }

    public Enumeration<String> getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String name) {
        return new String[0];
    }

    public Map<String, String[]> getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String name, Object o) {

    }

    public void removeAttribute(String name) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    public boolean isAsyncStarted() {
        return false;
    }

    public boolean isAsyncSupported() {
        return false;
    }

    public AsyncContext getAsyncContext() {
        return null;
    }

    public DispatcherType getDispatcherType() {
        return null;
    }
}
