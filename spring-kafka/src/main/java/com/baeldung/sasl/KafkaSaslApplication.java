package com.baeldung.sasl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSaslApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-sasl");
        SpringApplication.run(KafkaSaslApplication.class, args);
    }

}
