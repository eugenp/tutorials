package com.baeldung.contextvsunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.contextvsunit")
public class PersistenceContextVsUnitApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceContextVsUnitApplication.class, args);
    }

}
