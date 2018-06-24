package com.baeldung.reactive.eventstreamer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

/**
 * Created by Nakul on 23-06-2018.
 */
@SpringBootApplication
public class ReactiveClient {
    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(cr -> cr.bodyToFlux(Message.class))
                .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveClient.class).properties(Collections.singletonMap("server.port", "8081"))
            .run(args);
    }
}
