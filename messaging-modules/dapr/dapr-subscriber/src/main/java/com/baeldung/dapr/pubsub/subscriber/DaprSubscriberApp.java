package com.baeldung.dapr.pubsub.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaprSubscriberApp {

    public static void main(String[] args) {
        SpringApplication.run(DaprSubscriberApp.class, args);
    }
}
