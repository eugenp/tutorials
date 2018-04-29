package com.baeldung.demo.eventstreamwebfluxdemo.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class EventStreamClient {
    public static void main(String[] args)
    {
        WebClient.create("http://127.0.0.1:8080")
                 .get()
                 .uri("/events")
                 .accept(MediaType.APPLICATION_STREAM_JSON)
                 .retrieve()
                 .bodyToFlux(Long.class)
                 .toStream()
                 .forEach(item -> System.out.println("New event : " + item));
    }
}
