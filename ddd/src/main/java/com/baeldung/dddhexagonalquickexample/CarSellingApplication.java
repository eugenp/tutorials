package com.baeldung.dddhexagonalquickexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:ddd-quick.properties" })
@ComponentScan("com.baeldung.dddhexagonalquickexample")
public class CarSellingApplication {
    public static void main(final String[] args) {
        SpringApplication.run(CarSellingApplication.class, args);
    }
}
