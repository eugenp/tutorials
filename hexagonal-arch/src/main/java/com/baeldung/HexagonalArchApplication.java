package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.baeldung.*")
public class HexagonalArchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchApplication.class, args);
    }

}
