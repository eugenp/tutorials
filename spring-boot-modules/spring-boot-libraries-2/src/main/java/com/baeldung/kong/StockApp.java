package com.baeldung.kong;

import org.jobrunr.spring.autoconfigure.JobRunrAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class StockApp {

    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }

}
