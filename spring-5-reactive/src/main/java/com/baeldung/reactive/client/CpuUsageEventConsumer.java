package com.baeldung.reactive.client;

import com.baeldung.reactive.model.CpuUsageEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CpuUsageEventConsumer {

    @Bean
    WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner eventsConsumer(WebClient webClient) {
        return args -> webClient.get()
                         .uri("/events/stream")
                         .accept(MediaType.TEXT_EVENT_STREAM)
                         .exchange()
                         .flatMapMany(response -> response.bodyToFlux(CpuUsageEvent.class))
                         .subscribe(System.out::println);
    }

}
