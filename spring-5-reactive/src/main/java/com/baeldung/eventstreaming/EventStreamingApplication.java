package com.baeldung.eventstreaming;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EventStreamingApplication {
    public static void main(String[] args) {

        SpringApplication.run(EventStreamingApplication.class, args);

    }
}