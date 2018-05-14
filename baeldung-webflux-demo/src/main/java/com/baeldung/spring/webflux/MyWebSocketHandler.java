package com.baeldung.spring.webflux;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


public class MyWebSocketHandler implements WebSocketHandler {

    /**
     * Handle the WebSocket session.
     *
     * @param session the session to handle
     * @return completion {@code Mono<Void>} to indicate the outcome of the
     * WebSocket session handling.
     */
    @Override
    public Mono<Void> handle(WebSocketSession session) {

        return null;  //@todo
    }
}
