package com.baeldung.reactive.eventstreaming;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

public class NewsWebClient {

    public void consume() {

        WebClient client = WebClient.create("http://localhost:8080");

        Flux<NewsEvent> newsEventFlux = client.get()
                .uri("/news/stream")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(NewsEvent.class);

        newsEventFlux.subscribe(System.out::println);
    }
}
