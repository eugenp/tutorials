package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * use the appropriate profile: eventuate|modulith
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class, args);
    }

}
