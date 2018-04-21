package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        new ScheduledThreadPoolExecutor(1).schedule(
                () -> new Client().connect(),
                30,
                TimeUnit.SECONDS);
        SpringApplication.run(Server.class, args);
    }

    @Configuration
    public static class Router {
        @Bean
        public RouterFunction<ServerResponse> route() {
            return RouterFunctions.route(
                RequestPredicates.GET("/hello"),
                request -> ServerResponse.ok()
                    .body(BodyInserters.fromServerSentEvents(messages()))
            );
        }

        private static Flux<ServerSentEvent<Message>> messages() {
            return Flux.interval(Duration.ofSeconds(1)).
                map(id -> ServerSentEvent.<Message>builder()
                    .event("message")
                    .id(Long.toString(id))
                    .data(new Message(id, "Hello, World!"))
                    .build());
        }
    }
}
