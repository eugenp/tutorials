package com.baeldung.reactive.streaming.functional;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@Component
public class SequenceExampleHandler {

    public Mono<ServerResponse> sequence(ServerRequest request) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> sequence = Flux.fromStream(Stream.iterate(1, incSeq -> incSeq + 1));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.zip(interval, sequence).map(Tuple2::getT2), Integer.class);
    }

}
