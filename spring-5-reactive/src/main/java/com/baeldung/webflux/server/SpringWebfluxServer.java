package com.baeldung.webflux.server;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;

@SpringBootApplication(exclude = { ReactiveSecurityAutoConfiguration.class, ReactiveUserDetailsServiceAutoConfiguration.class })
public class SpringWebfluxServer {

    private static final Duration ONE_SEC = Duration.ofSeconds(1);

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringWebfluxServer.class).properties("server.port=9001")
            .run(args);
    }

    @Bean
    public HandlerFunction<ServerResponse> handleTimeRequest() {
        return request -> ServerResponse.ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(Flux.fromStream(Stream.generate(System::currentTimeMillis))
                .delayElements(ONE_SEC), Long.class);
    }

    @Bean
    public RouterFunction<ServerResponse> routeRequest(HandlerFunction<ServerResponse> requestHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/time"), requestHandler);
    }
}