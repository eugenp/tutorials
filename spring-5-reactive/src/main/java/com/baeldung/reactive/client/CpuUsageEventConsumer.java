package com.baeldung.reactive.client;

import com.baeldung.reactive.model.CpuUsageEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Krzysztof Majewski
 */
@Component
public class CpuUsageEventConsumer {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner eventsConsumer(WebClient client) {
        return args -> client.get()
                         .uri("/events/stream")
                         .accept(MediaType.TEXT_EVENT_STREAM)
                         .exchange()
                         .flatMapMany(cr -> cr.bodyToFlux(CpuUsageEvent.class))
                         .subscribe(System.out::println);
    }

}
