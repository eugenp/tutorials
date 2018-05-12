package com.baeldung.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveWebclient {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveWebclient.class);

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    ApplicationRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(logger::info);

        };
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ReactiveWebclient.class, args);
    }

}
