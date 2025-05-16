package com.baeldung.dapr.pubsub.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaprPublisherApp {

    public static void main(String[] args) {
        SpringApplication.run(DaprPublisherApp.class, args);
    }
}
