package com.ospyn.helpers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
@Component
@ServerEndpoint(value = "/push")
public class PushSocket {

    public static List<Session> socketSessions = Collections.synchronizedList(new ArrayList<Session>());

    @OnOpen
    public void open(Session session) {
        socketSessions.add(session);
    }

    @OnClose
    public void close(Session session) {
        socketSessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, final Session session) {

        System.out.println("Message from " + session.getId() + ": " + message);

    }
   public static void broadCast(String Message)
   {
       socketSessions.forEach(p-> {
           try {
               p.getBasicRemote().sendText(Message);
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
   }

}