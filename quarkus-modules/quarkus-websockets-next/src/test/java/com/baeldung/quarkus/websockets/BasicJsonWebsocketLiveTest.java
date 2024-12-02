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
public class BasicJsonWebsocketLiveTest {
    @Inject
    BasicWebSocketConnector connector;

    @Test
    public void whenSendingToTheJsonEndpoint_thenJsonIsReceivedBack() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        WebSocketClientConnection connection = connector
            .baseUri("http://localhost:8080/json")
            .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
            .onTextMessage((c, m) -> {
                messages.add(m);
                countDownLatch.countDown();
            })
            .connectAndAwait();

        connection.sendTextAndAwait("{\"message\": \"Hello\", \"other\": 1}");
        countDownLatch.await();
        connection.closeAndAwait();

        assertEquals(1, messages.size());
        // Note the lack of space in the middle, and no "other" field.
        // This is because the JSON was parsed and regenerated instead of this being a straight character echo.
        assertEquals("{\"message\":\"Hello\"}", messages.get(0));
    }
}
