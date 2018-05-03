package com.baeldung.reactive.eventstreaming;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class SampleWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(Flux.interval(Duration.ofSeconds(1))
                .map(Number::toString)
                .map(webSocketSession::textMessage));
    }

}
