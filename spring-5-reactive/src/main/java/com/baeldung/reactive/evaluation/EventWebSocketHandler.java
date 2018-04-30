package com.baeldung.reactive.evaluation;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

public class EventWebSocketHandler implements WebSocketHandler {

    private HealthService healthService;

    public EventWebSocketHandler(HealthService healthService) {
        this.healthService = healthService;
    }

    private Flux<String> statusFlux = Flux.generate(sink -> sink.next(healthService.isAlive()));

    private Flux<String> intervalStatusFlux = Flux.interval(Duration.ofSeconds(1L))
        .zipWith(statusFlux, (time, event) -> event);

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(intervalStatusFlux.map(webSocketSession::textMessage));
    }
}