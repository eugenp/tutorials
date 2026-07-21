package com.baeldung.cronfromdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CronFromDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronFromDbApplication.class, args);
    }
}