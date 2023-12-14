package com.baeldung.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/event-emitter")
public class WebSocketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        // Get session and WebSocket connection
        session.setMaxIdleTimeout(0);
        LOGGER.info("Get session and WebSocket connection");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Handle new messages
        LOGGER.info("Handle new messages -> {}", message );
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        LOGGER.info("WebSocket connection closes");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        LOGGER.info("Do error handling here");
    }
}
