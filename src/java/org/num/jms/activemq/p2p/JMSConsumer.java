package org.num.jms.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消费者消费 - 点对点模式
 * @author : zhuxueke
 * @since : 2017-12-15 14:51
 **/
public class JMSConsumer {
    // 默认的连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    // 默认的连接名密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    // 默认的连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args){
        // 连接工厂
        ConnectionFactory connectionFactory;
        // 连接
        Connection connection = null;
        // 会话，接收或发送消息的线程
        Session session;
        // 消息的目的地
        Destination destination;
        // 消息生产者
        MessageConsumer messageConsumer;
        // 实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME,JMSConsumer.PASSWORD,JMSConsumer.BROKEURL);
        try{
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue1");
            messageConsumer = session.createConsumer(destination);
            // reveiveMessage(messageConsumer);
            // 使用监听器接收消息
            messageConsumer.setMessageListener(new Listenr());
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    /**
     * 使用reveive 接收消息
     * @param messageConsumer
     */
    private static void reveiveMessage(MessageConsumer messageConsumer){
        try{
            while (true){
                TextMessage message = (TextMessage)messageConsumer.receive(100000);
                if(null != message){
                    System.out.println("收到消息：" + message.getText());
                    continue;
                }
                break;
            }
        }catch (JMSException e){
            e.printStackTrace();
        }
    }
}