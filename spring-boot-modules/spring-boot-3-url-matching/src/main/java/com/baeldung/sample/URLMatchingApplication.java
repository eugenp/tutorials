package com.baeldung.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class URLMatchingApplication {
    public static void main(String[] args) {
        SpringApplication.run(URLMatchingApplication.class, args);
    }
}
