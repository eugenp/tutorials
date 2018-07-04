package com.springwebflux.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ranjeetkaur
 *
 */
@SpringBootApplication(scanBasePackages = "com.springwebflux.*")
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}