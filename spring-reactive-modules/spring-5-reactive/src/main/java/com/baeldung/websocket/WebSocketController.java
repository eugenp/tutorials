package com.baeldung.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint("/event-emitter")
public class WebSocketController {

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        // Get session and WebSocket connection
        session.setMaxIdleTimeout(0);
        System.out.println("Get session and WebSocket connection");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Handle new messages
        System.out.println("Handle new messages -> " + message );
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        System.out.println("WebSocket connection closes");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        System.out.println("Do error handling here");
    }
}
