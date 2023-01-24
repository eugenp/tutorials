package com.baeldung.reactive.debugging.server.handlers;

import com.baeldung.reactive.debugging.server.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ServerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    public Mono<ServerResponse> useHandler(final ServerRequest request) {
        // there are chances that something goes wrong here...
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(getFlux(), Foo.class);
    }

    public Mono<ServerResponse> useHandlerFinite(final ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.range(0, 50)
                .map(sequence -> new Foo(new Long(sequence), "theFooNameNumber" + sequence)
                ), Foo.class);
    }

    private static Flux<Foo> getFlux() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> {
              LOGGER.info("retrieving Foo. Sequence: {}", sequence);
              if (ThreadLocalRandom.current().nextInt(0, 50) == 1) {
                  throw new RuntimeException("There was an error retrieving the Foo!");
              }
              return new Foo(sequence, "name" + sequence);
          });
    }
}
