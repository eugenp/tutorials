package com.baeldung.reactive.evaluation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;

@Component
public class Client implements CommandLineRunner {

    @Override
    public void run(String... args) {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
            URI.create("ws://localhost:8080/events"), 
            session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .log()
                .then())
            .block();
    }
}