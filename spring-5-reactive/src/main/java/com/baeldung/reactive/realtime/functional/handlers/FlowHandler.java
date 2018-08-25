package com.baeldung.reactive.realtime.functional.handlers;

import com.baeldung.reactive.realtime.model.Event;
import com.baeldung.reactive.realtime.service.RealtimeEventGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlowHandler {

    @Autowired
    private RealtimeEventGeneratorService realtimeEventGeneratorService;

    public Mono<ServerResponse> flow(final ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(realtimeEventGeneratorService.flow(), Event.class);
    }
}
