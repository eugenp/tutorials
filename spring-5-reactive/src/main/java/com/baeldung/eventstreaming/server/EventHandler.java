package com.baeldung.eventstreaming.server;
import com.baeldung.eventstreaming.model.Event;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class EventHandler {

    public Mono<ServerResponse> eventEmitted(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(emitEvent(), Event.class);
    }

    private Flux<Event> emitEvent() {
        return Flux.<Event>create(eventFluxSink -> {
            int index = 1;
            while (!eventFluxSink.isCancelled()) {
                eventFluxSink.next(new Event("Event number " + index++));
                sleep();
            }
        }).share();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}