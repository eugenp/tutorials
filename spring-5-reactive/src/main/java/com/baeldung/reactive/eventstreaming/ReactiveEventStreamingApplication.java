package com.baeldung.reactive.eventstreaming;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveEventStreamingApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReactiveEventStreamingApplication.class, args);
    }


    @Bean
    CommandLineRunner getRandomEvent() {

        WebClient client = WebClient.create("http://localhost:8080");
        return args -> client.get()
                .uri("/sse/randomEvent")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(Event.class))
                .subscribe(System.out::println);
    }
}

