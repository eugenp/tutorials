package com.baeldung.reactive.webflux;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

@Component
public class EmployeeWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(new EmployeeRepository().findAllEmployees()
            .map(Employee::getName)
            .map(webSocketSession::textMessage));
    }

}
