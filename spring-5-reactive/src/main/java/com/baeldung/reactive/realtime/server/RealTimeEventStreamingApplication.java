package com.baeldung.reactive.realtime.server;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealTimeEventStreamingApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RealTimeEventStreamingApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        app.run(args);
    }

}
