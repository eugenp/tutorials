package com.baeldung.dlt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaDltApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaDltApplication.class, args);
    }
}
