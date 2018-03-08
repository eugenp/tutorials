package org.baeldung.spring.webflux.securityincidents.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class SecurityEventRouter {
    @Bean
    public RouterFunction<ServerResponse> routes(SecurityEventHandler handler) {
        return RouterFunctions
                .route(GET("/securityevents/{size}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                            handler::getSecurityEvents)
                .andRoute(GET("/securityeventsstream")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                            handler::getSecurityEventsStream);
    }
}