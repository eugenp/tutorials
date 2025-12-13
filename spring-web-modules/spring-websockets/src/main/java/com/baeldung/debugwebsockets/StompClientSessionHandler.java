package com.baeldung.debugwebsockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.Map;

public class StompClientSessionHandler extends StompSessionHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger("StompClientSessionHandler");

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established. Session Id -> {}", session.getSessionId());
        session.subscribe("/topic/ticks", this);
        logger.info("Subscribed to topic: /topic/ticks");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("Payload -> {}", payload);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Map.class;
    }
}
