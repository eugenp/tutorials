package com.baeldung.reactive.webclient;

import java.util.Collections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import com.baeldung.reactive.model.FruitStatus;

@SpringBootApplication
public class ReactiveWebClientApplication {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080/fruit/mango");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(FruitStatus.class))
                .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveWebClientApplication.class).properties(Collections.singletonMap("server.port", "8090"))
            .run(args);
    }
    
}
