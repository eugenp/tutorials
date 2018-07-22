package com.baeldung.reactive.webflux.eventstreaming;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EventWebSocketHandler implements WebSocketHandler {

    MessageFormat mf = new MessageFormat("EventID: {0} , Event Time: {1}") ;
    
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
            Flux.<String> generate(sink -> sink.next(
                mf.format(new Object[] {UUID.randomUUID(),LocalTime.now()})))
            .delayElements(Duration.ofSeconds(1))
            .map(session::textMessage));
    }

}
