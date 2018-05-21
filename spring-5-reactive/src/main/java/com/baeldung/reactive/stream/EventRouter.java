/**
 *
 */
package com.baeldung.reactive.stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import java.time.Duration;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * An example how to configure request mapping using {@link RouterFunction}.
 * @author goobar
 *
 */
@Configuration
public class EventRouter {
    static final String EVENT_URI = "/router/events";

    /**
     * @return the configured {@link RouterFunction} that will route to event stream handler
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET(EVENT_URI), this::response);
    }

    /**
     * @return
     */
    private Publisher<Event> eventStream() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(Event::of);
    }

    /**
     * @param request
     * @return
     */
    private Mono<ServerResponse> response(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(eventStream(), Event.class);
    }

}
