package com.baeldung.reactive.client;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class ReactiveWebClient {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/integers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(cr -> cr.bodyToFlux(Integer.class))
                .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        System.getProperties()
            .put("server.port", "8283");
        new SpringApplicationBuilder(ReactiveWebClient.class).properties(Collections.singletonMap("server.port", "8283"))
            .run(args);
    }
}
