package com.baeldung.reactive;

import com.baeldung.reactive.model.SimpleEvent;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
public class WebClientApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebClientApp.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(args);
        WebClient webClient = WebClient.create("http://localhost:8080");

        webClient.get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(SimpleEvent.class)
                .subscribe(System.out::println);
    }
}
