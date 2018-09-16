package com.baeldung.reactive.publisherconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@SpringBootApplication
public class EventsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EventsConsumerApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);

        WebClient.create("http://localhost:8080")
                .get().uri("/uuids")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .block().bodyToFlux(String.class)
                .subscribe(System.out::println);
    }
}