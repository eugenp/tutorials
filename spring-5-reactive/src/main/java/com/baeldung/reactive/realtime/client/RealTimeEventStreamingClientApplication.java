package com.baeldung.reactive.realtime.client;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.realtime.server.Event;

@SpringBootApplication
public class RealTimeEventStreamingClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RealTimeEventStreamingClientApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8085"));
        app.run(args);
    }

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8084");
    }

    @Bean
    CommandLineRunner commandLineRunner(WebClient client) {
        return args -> {

            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(r -> r.bodyToFlux(Event.class))
                .subscribe(System.out::println);

        };
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }

}
