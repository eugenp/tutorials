package com.baeldung.reactive.memory;

import java.time.Duration;
import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class MemoryUtilizationApplication {

    @Bean
    RouterFunction<?> routes() {
        return RouterFunctions.route(RequestPredicates.GET("/freememory"), 
          req -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.interval(Duration.ofSeconds(1))
                    .map(l -> Runtime.getRuntime().freeMemory()),
                  Long.class));
    }

    @Bean
    WebClient webclient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    ApplicationRunner run(WebClient webClient) {
        return args -> {
            webClient.get()
              .uri("/freememory")
              .accept(MediaType.TEXT_EVENT_STREAM)
              .retrieve()
              .bodyToFlux(Long.class)
              .subscribe(memoryUtilization -> System.out.println("At time " + new Date() + ", Memory utilization is " + memoryUtilization));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MemoryUtilizationApplication.class, args);
    }
}
