package com.baeldung.jdbcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baledung.jdbcclient")
public class JdbcClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcClientDemoApplication.class, args);
    }
}
