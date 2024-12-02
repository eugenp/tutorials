package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket(path = "/echo")
public class EchoWebsocket {
    private static final Logger LOG = LoggerFactory.getLogger(EchoWebsocket.class);

    @OnOpen
    public String onOpen() {
        LOG.info("Connection opened");
        return "Hello";
    }

    @OnTextMessage
    public String onTextMessage(String message, WebSocketConnection connection) {
        LOG.info("Received message: {}", message);
        return message;
    }
}
