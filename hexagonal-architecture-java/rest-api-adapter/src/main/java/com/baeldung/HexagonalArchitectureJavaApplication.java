package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.hexagonal")
@EnableAutoConfiguration
@SpringBootApplication
public class HexagonalArchitectureJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureJavaApplication.class, args);
    }

}
