package org.num.util.sax;

import com.sun.org.apache.bcel.internal.util.ClassPath;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author : zhuxueke
 * @since : 2017-11-28 12:49
 **/
public class XmlSaxTest {
    public static void main(String[] args) {
        try{
            URL url = XmlSaxTest.class.getResource("/myclass.xml");
            URI uri = url.toURI();
            String path = uri.getPath();
            ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)SaxSerice.ReadXML(path,"class");
            System.out.println(list.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
