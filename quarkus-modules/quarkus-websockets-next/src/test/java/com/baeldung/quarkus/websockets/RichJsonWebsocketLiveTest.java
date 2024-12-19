package com.baeldung.quarkus.websockets;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.*;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RichJsonWebsocketLiveTest {
    @Inject
    WebSocketConnector<JsonWebsocketClient> connector;

    @Inject
    Instance<JsonWebsocketClient> clients;

    @Test
    public void whenSendingToTheJsonEndpoint_thenTheSameJsonIsReceivedBack() throws InterruptedException {
        WebSocketClientConnection connection = connector
            .baseUri("http://localhost:8080")
            .connectAndAwait();

        JsonWebsocketClient websocketClient = clients.get();

        websocketClient.send("Hello");

        JsonWebsocketClient.Message message = websocketClient.getMessage();
        assertEquals("Hello", message.message);

        connection.closeAndAwait();
    }

    @WebSocketClient(path = "/json")
    static class JsonWebsocketClient {
        private static final Logger LOG = LoggerFactory.getLogger(JsonWebsocketClient.class);

        private BlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

        private WebSocketClientConnection connection;

        private CountDownLatch connectLatch = new CountDownLatch(1);

        @OnOpen
        void onOpen(WebSocketClientConnection connection) {
            this.connection = connection;
            this.connectLatch.countDown();
        }

        void send(String message) throws InterruptedException {
            this.connectLatch.await();
            this.connection.sendTextAndAwait(new Message(message));
        }

        @OnTextMessage
        void onMessage(Message message, WebSocketClientConnection connection) {
            LOG.info("Received message {} from connection {}", message, connection);

            messages.add(message);
        }

        Message getMessage() throws InterruptedException {
            return this.messages.take();
        }

        record Message(String message) {}
    }
}
