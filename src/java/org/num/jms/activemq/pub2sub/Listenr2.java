package org.num.jms.activemq.pub2sub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;

/**
 * 使用监听接收消息
 * @author : xp
 * @since : 2017-12-15 15:21
 **/
public class Listenr2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
            System.out.println("订阅者2收到的消息：" + ((TextMessage)message).getText() + " " + date);
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}
