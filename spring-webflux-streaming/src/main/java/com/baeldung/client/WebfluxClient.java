package com.baeldung.client;

import com.baeldung.model.Data;
import java.util.Collections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxClient {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080"); //reference: http://www.baeldung.com/spring-5-webclient
    }

    @Bean
    CommandLineRunner run(WebClient client) {
        return args -> {
            client.get()
              .uri("/data").
              accept(MediaType.TEXT_EVENT_STREAM).
              exchange().
              flatMapMany(cr -> cr.bodyToFlux(Data.class)).
              subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebfluxClient.class).properties(Collections.singletonMap("server.port", "8081")).run(args);
    }
}
