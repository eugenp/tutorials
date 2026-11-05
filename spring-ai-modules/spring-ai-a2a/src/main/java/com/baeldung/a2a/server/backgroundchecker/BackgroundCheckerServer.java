package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-background-checker-server.properties")
public class BackgroundCheckerServer {

    public static void main(String[] args) {
        SpringApplication.run(BackgroundCheckerServer.class, args);
    }
}