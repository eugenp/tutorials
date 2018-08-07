package com.baeldung.event.streaming.server.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.event.streaming.model.Transaction;
import com.baeldung.event.streaming.server.generator.TransactionGenerator;

import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

    @Autowired
    private TransactionGenerator transactionGenerator;

    public Mono<ServerResponse> transactionGenerated(ServerRequest serverRequest) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(transactionGenerator.generate(), Transaction.class);
    }
}