package com.baeldung.debugwebsockets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * This should be part of integration test suite.
 * The test starts the server and then connects to the WebSocket. Then verifies if the messages are received from the
 * WebSocket.
 * This test is inspired from: https://github.com/spring-guides/gs-messaging-stomp-websocket/blob/main/complete/src/test/java/com/example/messagingstompwebsocket/GreetingIntegrationTests.java
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketIntegrationTest{
    WebSocketClient client;
    WebSocketStompClient stompClient;
    @LocalServerPort
    private int port;
    private static final Logger logger= LoggerFactory.getLogger(WebSocketIntegrationTest.class);

    @BeforeEach
    public void setup() {
        logger.info("Setting up the tests ...");
        client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    void givenWebSocket_whenMessage_thenVerifyMessage() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        StompSessionHandler sessionHandler = new StompSessionHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                logger.info("Connected to the WebSocket ...");
                session.subscribe("/topic/ticks", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Map.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        try {

                            assertThat(payload).isNotNull();
                            assertThat(payload).isInstanceOf(Map.class);

                            @SuppressWarnings("unchecked")
                            Map<String, Integer> map = (Map<String, Integer>) payload;

                            assertThat(map).containsKey("HPE");
                            assertThat(map.get("HPE")).isInstanceOf(Integer.class);
                        } catch (Throwable t) {
                            failure.set(t);
                            logger.error("There is an exception ", t);
                        } finally {
                            session.disconnect();
                            latch.countDown();
                        }

                    }
                });
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
            }
        };
        stompClient.connect("ws://localhost:{port}/stock-ticks/websocket", sessionHandler, this.port);
        if (latch.await(20, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                fail("Assertion Failed", failure.get());
            }
        } else {
            fail("Could not receive the message on time");
        }
    }
}
