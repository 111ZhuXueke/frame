package org.num.util.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zhuxueke
 * @since : 2017-11-28 12:48
 **/
public class Myhandler  extends DefaultHandler {
    /*存储所有正在解析的元素的数据*/
    private Map<String,String> map = null;
    /*存储所有解析的元素的数据*/
    private List<Map<String,String>> list = null;
    /*正在解析元素的名字*/
    String currentTag = null;
    /*正在解析元素的值*/
    String currentValue=null;
    /*正在解析元素的节点名称*/
    String nodeName = null;

    public Myhandler(String nodeName){
        this.nodeName = nodeName;
    }
    @Override
    public void startDocument() throws SAXException {
        System.out.println("----start Document-----");
        list = new ArrayList<Map<String,String>>();
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("----end Document-----");
        super.endDocument();
    }

    /**
     * 解析到每个元素的内容时触发
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("----characters----");
        if(currentTag!= null && map!=null){
            currentValue = new String(ch,start,length);
            //如果内容不等于空或者空格和换行符，将其存入map
            if(currentValue != null && !"".equals(currentValue.trim()) && !"\n".equals(currentValue.trim())){
                map.put(currentTag, currentValue);
            }
            //当前的元素已解析过，将其置于下一个元素的解析
            currentTag = null;
            currentValue = null;
        }
    }

    /**
     * 解析元素的节点和属性
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("----start Element----" + qName);
        //判断正在解析的元素是不是开始解析的元素
        if(qName.equals(nodeName)){
            map = new HashMap<String, String>();
        }
        //判断解析的元素是否有属性值，如果有则将其全部存入map对象中
        if(attributes != null && map != null){
            for (int i = 0; i < attributes.getLength(); i++) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
        }
        //正在解析元素
        currentTag = qName;
    }



    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("----endElement----"+qName);
        //判断是否作为一个节点结束的元素标签
        if(qName.equals(nodeName)){
            list.add(map);
            map = null;
        }
    }
    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }
}
