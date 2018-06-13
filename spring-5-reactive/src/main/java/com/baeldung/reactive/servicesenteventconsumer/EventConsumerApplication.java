package com.baeldung.reactive.servicesenteventconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventConsumerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EventConsumerApplication.class);
    }
}
