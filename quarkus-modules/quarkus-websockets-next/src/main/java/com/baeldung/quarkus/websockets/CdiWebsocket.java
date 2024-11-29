package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import jakarta.inject.Inject;

@WebSocket(path = "/cdi")
public class CdiWebsocket {
    @Inject
    private CdiService cdiService;

    @OnTextMessage(broadcast = true)
    public String onTextMessage(String message) {
        return message + ": from " + cdiService.getConnectionId();
    }
}
