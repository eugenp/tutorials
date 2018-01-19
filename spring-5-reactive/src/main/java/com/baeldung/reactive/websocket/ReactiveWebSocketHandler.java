package com.baeldung.reactive.websocket;

import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private Flux<Event> eventFlux;
    private Flux<Event> intervalFlux;

    /**
     * Here we prepare a Flux that will emit a message every second
     */
    @PostConstruct
    private void init() throws InterruptedException {

        eventFlux = Flux.generate(e -> {
            Event event = new Event(UUID.randomUUID()
                .toString(),
                LocalDateTime.now()
                    .toString());
            e.next(event);
        });

        intervalFlux = Flux.interval(Duration.ofMillis(1000L))
            .zipWith(eventFlux, (time, event) -> event);

    }

    /**
     * On each new client session, send the message flux to the client.
     * Spring subscribes to the flux and send every new flux event to the WebSocketSession object
     * @param session
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        ObjectMapper json = new ObjectMapper();
        return webSocketSession.send(intervalFlux.map(event -> {
            try {
                String jsonEvent = json.writeValueAsString(event);
                System.out.println(jsonEvent);
                return jsonEvent;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        })
            .map(webSocketSession::textMessage))

            .and(webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .log());
    }

}
