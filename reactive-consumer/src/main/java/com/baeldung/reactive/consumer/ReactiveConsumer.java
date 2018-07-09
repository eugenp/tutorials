package com.baeldung.reactive.consumer;

import java.util.Collections;

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
                    .uri("/stockprice")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange()
                    .flatMapMany(cr -> cr.bodyToFlux(StockPriceEvent.class))
                    .subscribe(new StockPriceEventSubscriber());
                    
        };

    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveConsumer.class)
        .properties(Collections.singletonMap("server.port", "9000"))
                .run(args);            
    }

}