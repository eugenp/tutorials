package com.baeldung.quarkus.websockets;

import io.quarkus.websockets.next.OpenConnections;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CdiService {
    @Inject
    WebSocketConnection connection;

    @Inject
    OpenConnections connections;

    public String getConnectionId() {
        return connection.id();
    }

    public void sendToAll(String message) {
        connections.forEach(connection -> connection.sendTextAndAwait(message));
    }
}
