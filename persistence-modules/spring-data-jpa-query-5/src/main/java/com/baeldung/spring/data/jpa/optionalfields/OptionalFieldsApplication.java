package com.baeldung.spring.data.jpa.optionalfields;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("h2")
@SpringBootApplication
public class OptionalFieldsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OptionalFieldsApplication.class);
    }
}
