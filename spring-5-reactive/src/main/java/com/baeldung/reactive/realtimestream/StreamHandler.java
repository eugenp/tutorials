package com.baeldung.reactive.realtimestream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StreamHandler {

    private StreamService streamService;

    public StreamHandler(StreamService streamService) {
        this.streamService = streamService;
    }

    public Mono<ServerResponse> stream(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(BodyInserters.fromPublisher(streamService.infiniteEventStream(), StreamEvent.class));
    }

}
