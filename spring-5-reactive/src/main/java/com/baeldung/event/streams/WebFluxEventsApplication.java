package com.baeldung.event.streams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
public class WebFluxEventsApplication {

    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner clientDemo(WebClient client) {
        return args -> client.get()
            .uri("/users")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(User.class)
            .subscribe(user -> log.info(String.valueOf(user)));
    }

    public static void main(String[] args) {
        SpringApplication.run(WebFluxEventsApplication.class, args);
    }

}
