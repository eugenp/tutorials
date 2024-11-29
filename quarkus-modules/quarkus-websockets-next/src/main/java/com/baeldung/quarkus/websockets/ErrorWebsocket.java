package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/error")
public class ErrorWebsocket {

    @OnTextMessage(broadcast = true)
    public String onTextMessage(String message) {
        throw new RuntimeException("Oops: " + message);
    }

    @OnError
    public String onError(RuntimeException e) {
        return e.toString();
    }
}
