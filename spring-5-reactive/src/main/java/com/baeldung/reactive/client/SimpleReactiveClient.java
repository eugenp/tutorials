package com.baeldung.reactive.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.Event;

@Component
public class SimpleReactiveClient implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        WebClient client = WebClient.create("http://localhost:8080");

        client.get()
            .uri("events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(Event.class)
            .subscribe(System.out::println);

    }

}
