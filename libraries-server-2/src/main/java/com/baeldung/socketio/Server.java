package com.baeldung.socketio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8081);

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            LOG.info("New connection from client {}", client.getRemoteAddress());
            client.joinRoom("testroom");

            client.getNamespace().getRoomOperations("testroom")
                .sendEvent("message", "Hello from " + client.getRemoteAddress());
        });

        server.addDisconnectListener(client -> {
            LOG.info("Disconnection from client {}", client.getRemoteAddress());
        });

        server.addEventListener("message",String.class, (client, message, request) -> {
            LOG.info("Received message from client {}: {}", client.getRemoteAddress(), message);
        });

        server.start();

        Thread.currentThread().join();
    }
}
