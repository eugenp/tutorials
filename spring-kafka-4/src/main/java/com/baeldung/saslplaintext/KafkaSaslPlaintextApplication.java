package com.baeldung.saslplaintext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaSaslPlaintextApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-sasl-plaintext");
        SpringApplication.run(KafkaSaslPlaintextApplication.class, args);
    }
}
