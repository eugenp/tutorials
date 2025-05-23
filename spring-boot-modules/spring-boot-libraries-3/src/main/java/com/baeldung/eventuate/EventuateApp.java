package com.baeldung.eventuate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication(exclude = {
    KafkaAutoConfiguration.class
})
public class EventuateApp {

    public static void main(String[] args) {
        SpringApplication.run(EventuateApp.class, args);
    }

}
