/**
 *
 */
package com.baeldung.reactive.stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * An example how to configure request mapping using {@link RouterFunction}.
 * @author goobar
 *
 */
@Configuration
public class EventRouter {
    /**
     * Event stream URI.
     */
    public static final String EVENT_URI = "/router/events";

    /**
     * @param generator an event stream generator strategy
     * @return the configured {@link RouterFunction} that will route to event stream handler
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(EventStreamGenerator generator) {
        return RouterFunctions.route(GET(EVENT_URI), request -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(generator.events(), Event.class));
    }

}
