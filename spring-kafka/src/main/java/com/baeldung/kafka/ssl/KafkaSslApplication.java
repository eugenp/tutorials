package com.baeldung.kafka.ssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSslApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSslApplication.class, args);
    }

}
