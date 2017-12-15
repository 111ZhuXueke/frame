package org.num.jms.activemq.p2p;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 使用监听接收消息
 * @author : zhuxueke
 * @since : 2017-12-15 15:21
 **/
public class Listenr implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("收到的消息：" + ((TextMessage)message).getText());
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}
