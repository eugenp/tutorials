package com.baeldung.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.baeldung.model.Message;

@ServerEndpoint(
        value="/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class
)
public class ChatEndpoint {
    private Session session;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        System.out.println(session.getId() + " connected!");

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        System.out.println(message.toString());

        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(session.getId() + " disconnected!");
        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.toString());
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        for (ChatEndpoint endpoint : chatEndpoints) {
            synchronized (endpoint) {
                endpoint.session.getBasicRemote().sendObject(message);
            }
        }
    }

}
