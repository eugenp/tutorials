package com.baeldung.reactive.serversentevents.server;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerSSEApplication {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerSSEApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

}
