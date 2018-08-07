package com.baeldung.event.streaming.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EventStreamingApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(EventStreamingApplicationServer.class, args);
    }
}
