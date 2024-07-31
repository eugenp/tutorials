package com.baeldung.restvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.restvalidation" })
public class RestValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestValidationApplication.class, args);
    }

}