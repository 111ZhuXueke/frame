package org.num.util.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import java.util.Map;

/**
 * @author : zhuxueke
 * @since : 2017-11-28 12:49
 **/
public class SaxSerice {
    public static List<Map<String,String>> ReadXML(String uri, String nodeName) throws Exception{
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        Myhandler my = new Myhandler("stu");
        parser.parse(uri, my);
        return my.getList();
    }
}
