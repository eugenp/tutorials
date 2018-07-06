package com.baeldung.reactive.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveConsumer {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }
    

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                    .uri("/integers")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange()
                    .flatMapMany(cr -> cr.bodyToFlux(Integer.class))
                    .subscribe(new ExampleSubscriber<Integer>());
                    
        };

    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveConsumer.class)
                .run(args);            
    }

}