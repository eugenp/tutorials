package com.baeldung.reactive.publisherconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class EventsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsConsumerApplication.class, args);

        //create our consumer of events
        WebClient.create("http://localhost:8080")
                .get().uri("/uuids")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .block().bodyToFlux(String.class)
                .subscribe(System.out::println);
    }
}