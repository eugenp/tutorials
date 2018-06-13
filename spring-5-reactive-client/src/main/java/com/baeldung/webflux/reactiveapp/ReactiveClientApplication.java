package com.baeldung.webflux.reactiveapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
@Profile(value = "client")
public class ReactiveClientApplication {

    @Bean
    WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner commandLineRunner(WebClient client) {
        return args -> get(client);
    }

    private void get(WebClient client) {
        client
                .get()
                .uri("/emitEvents")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(clientResponse -> clientResponse.bodyToFlux(Event.class))
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveClientApplication.class)
                .properties(Collections.singletonMap("server.port", 8081)).run(args);
    }
}
