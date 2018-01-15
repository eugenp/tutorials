package com.baeldung;

import java.net.URI;
import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveWebSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebSocketApplication.class, args);
    }
    
    /**
     * Spring Reactive WebSocket Client
     * **/
    @Bean
    CommandLineRunner runner() {
        return run -> {
            WebSocketClient client = new ReactorNettyWebSocketClient();
            client.execute(URI.create("ws://localhost:8080/event-emitter"), session -> session.send(Mono.just(session.textMessage("event-me-from-spring-reactive-client")))
                .thenMany(session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .log())
                .then())
            .block();
//             .block(Duration.ofSeconds(10L));//force timeout after given duration
        };
    }
}
