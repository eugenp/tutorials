package com.baeldung.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.actuator")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}