package com.baeldung.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Spring5ReactiveTestApplication {

    @Bean
    public WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveTestApplication.class, args);
    }
    
}
