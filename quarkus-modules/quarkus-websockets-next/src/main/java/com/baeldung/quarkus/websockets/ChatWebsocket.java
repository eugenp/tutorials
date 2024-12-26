package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.PathParam;
import io.quarkus.websockets.next.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket(path = "/chat/:user")
public class ChatWebsocket {
    private static final Logger LOG = LoggerFactory.getLogger(ChatWebsocket.class);

    @OnTextMessage(broadcast = true)
    public String onTextMessage(String message, @PathParam("user") String user) {
        LOG.info("Received message: {}", message);
        return user + ": " + message;
    }

}
