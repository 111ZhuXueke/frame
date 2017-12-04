package org.num.tomcatRun.demo02;

import org.num.tomcatRun.demo01.HttpServer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * HttpServletResponse
 * @author : xp
 * @since : 2017-12-01 15:41
 **/
public class Response implements ServletResponse{
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream outputStream;
    PrintWriter writer;

    /**
     * 定向到文件
     * @author : xp
     * @since : 2017/12/1 15:49
     */
    public void sendStaticResource() throws IOException{
        char[] bytes = new char[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getServletPath());
            if (file.exists()){
                fis = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fis,"utf-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String str = br.readLine();
                while (str != null){
                    outputStream.write(str.getBytes("utf-8"), 0, str.length());
                    str = br.readLine();
                }
            }else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n"
                        + "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fis != null) fis.close();
        }
    }

    public Response(OutputStream output) {
        this.outputStream = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public String getContentType() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // 设置自动刷新
        writer = new PrintWriter(outputStream,true);
        return writer;
    }

    public void setCharacterEncoding(String charset) {

    }

    public void setContentLength(int len) {

    }

    public void setContentLengthLong(long len) {

    }

    public void setContentType(String type) {

    }

    public void setBufferSize(int size) {

    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {

    }

    public void setLocale(Locale loc) {

    }

    public Locale getLocale() {
        return null;
    }
}
