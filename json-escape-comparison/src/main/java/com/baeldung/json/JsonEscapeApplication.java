package com.baeldung.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class JsonEscapeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonEscapeApplication.class, args);
    }
}
