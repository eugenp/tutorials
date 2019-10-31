package com.baeldung.integrationtesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuredApplication.class, args);
    }

}
