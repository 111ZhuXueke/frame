package org.num.util.ReverseAjax;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author : zhuxueke
 * @since : 2017-12-01 10:42
 **/
@ServerEndpoint("/websocket")
public class WebSocket {
    private static final Queue<WebSocket> CONNECTIONS = new ConcurrentLinkedQueue<WebSocket>();
    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        CONNECTIONS.offer(this);
    }

    @OnMessage
    public void onMessage(String message ){
        broadcast(message);
    }

    @OnClose
    public void onClose() {
        CONNECTIONS.remove(this);
    }

    private synchronized void broadcast(String msg) {
        for (WebSocket point : CONNECTIONS) {
            try {
                point.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                CONNECTIONS.remove(point);
                try {
                    point.session.close();
                } catch (IOException e1) {

                }
            }
        }
    }
}
