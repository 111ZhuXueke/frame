package org.num.tomcatRun.demo01;

import java.io.*;

/**
 * HttpServletResponse
 * @author : zhuxueke
 * @since : 2017-12-01 15:41
 **/
public class Response {
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream outputStream;

    /**
     * 定向到文件
     * @author : zhuxueke
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
}
