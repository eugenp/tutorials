package com.baeldung.quarkus.websockets;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ChatWebsocketLiveTest {
    @Inject
    BasicWebSocketConnector connector;

    @Test
    public void whenSendingToTheChatEndpoint_thenTheSameMessageIsReceivedBackWithTheUsername() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        WebSocketClientConnection connection = connector
            .baseUri("http://localhost:8080/chat/test")
            .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
            .onTextMessage((c, m) -> {
                messages.add(m);
                countDownLatch.countDown();
            })
            .connectAndAwait();

        connection.sendTextAndAwait("Hello, World!");
        countDownLatch.await();
        connection.closeAndAwait();

        assertEquals(1, messages.size());
        assertEquals("test: Hello, World!", messages.get(0));
    }
}
