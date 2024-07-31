package com.baeldung.validation.listvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.baeldung")
@Configuration
@SpringBootApplication
public class SpringListValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringListValidationApplication.class, args);
    }

}
