package com.baeldung.h2seq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class H2SeqDemoApplication {

    public static void main(String... args) {
        SpringApplication.run(H2SeqDemoApplication.class, args);
    }

}