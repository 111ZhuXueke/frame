package org.num.jms.activemq.p2p;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者 - 点对点模式
 * @author : zhuxueke
 * @since : 2017-12-15 14:26
 **/
public class JMSProducts {
    // 默认的连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    // 默认的连接名密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    // 默认的连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    // 发送消息的数量
    private static final int SENDNUM1 = 10;

    public static void main(String[] arg){
        // 连接工厂
        ConnectionFactory connectionFactory;
        // 连接
        Connection connection = null;
        // 会话，接收或发送消息的线程
        Session session;
        // 消息的目的地
        Destination destination;
        // 消息生产者
        MessageProducer messageProducer;
        // 实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSProducts.USERNAME,JMSProducts.PASSWORD,JMSProducts.BROKEURL);
        try{
            connection = connectionFactory.createConnection();
            // 启动连接
            connection.start();
            // 当客户端从 receive 或 onMessage成功返回时，Session 自动签收客户端的这条消息的收条
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            // 创建消息队列
            destination = session.createQueue("FirstQueue1");
            // 创建消息队列
            messageProducer = session.createProducer(destination);
            // 发送消息
            sendMessage(session,messageProducer);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送消息
     * @param session
     * @param messageProducer
     * @throws Exception
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception{
        for(int i =0; i< JMSProducts.SENDNUM1; i++){
            TextMessage message = session.createTextMessage("ActiveMQ发送的消息：" + i);
            messageProducer.send(message);
        }
    }
}
