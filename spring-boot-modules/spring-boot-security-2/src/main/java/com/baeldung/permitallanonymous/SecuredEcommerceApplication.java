package com.baeldung.permitallanonymous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baeldung.permitallanonymous.*")
public class SecuredEcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecuredEcommerceApplication.class, args);
    }
}
