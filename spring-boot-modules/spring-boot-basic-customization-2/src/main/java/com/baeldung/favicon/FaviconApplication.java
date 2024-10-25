package com.baeldung.favicon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("favicon")
@SpringBootApplication(scanBasePackages = "com.baeldung.favicon")
public class FaviconApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaviconApplication.class, args);
    }
}
