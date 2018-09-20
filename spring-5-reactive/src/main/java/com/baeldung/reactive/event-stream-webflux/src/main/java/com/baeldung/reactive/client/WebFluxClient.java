package com.baeldung.reactive.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Date;

@SpringBootApplication
@ComponentScan("com.baeldung")
public class WebFluxClient {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8081");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                    .uri("/events")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .retrieve()
                    .bodyToFlux(Date.class)
                    .map(s -> String.valueOf(s))
                    .log()
                    .subscribe();
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebFluxClient.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(args);
    }
}
