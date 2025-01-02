package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/json")
public class JsonWebsocket {

    @OnTextMessage
    public Message onTextMessage(Message message) {
        return message;
    }

    record Message(String message) {}
}
