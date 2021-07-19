package com.baeldung.spring.cloud.ribbon.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RibbonWeatherServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(RibbonWeatherServiceApp.class, args);
    }
}
