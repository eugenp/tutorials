package com.baeldung.spring.kafka.multipletopics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaMultipleTopicsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaMultipleTopicsApplication.class, args);
    }
}