package com.baeldung.reactive.eventstream.server;

import com.baeldung.reactive.model.Foo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@SpringBootApplication
@EnableWebFlux
@Slf4j
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(GET("/foos").and(contentType(TEXT_EVENT_STREAM)), serverRequest -> ServerResponse.ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(fooInfiniteStream(), Foo.class));
    }

    private Flux<Foo> fooInfiniteStream() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(i -> new Foo(i, randomAlphabetic(8)))
            .doOnNext(f -> log.info(f.toString()));
    }
}
