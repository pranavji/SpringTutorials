package com.ospyn.helpers;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
   public static void broadCast(String message)
   {
       socketSessions.forEach(p-> {
           try {
               p.getBasicRemote().sendText(message);
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
   }

}