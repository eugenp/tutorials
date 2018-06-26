package com.baeldung.reactive.sse.model;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

public class EventSubscription {

    private DirectProcessor<ServerSentEvent> directProcessor;

    public EventSubscription() {
        this.directProcessor = DirectProcessor.create();
    }

    public void emit(ServerSentEvent e) {
        directProcessor.onNext(e);
    }

    public Flux<ServerSentEvent> subscribe() {
        return directProcessor;
    }
}
