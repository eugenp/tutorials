package com.shaheen.hexa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shaheen.hexa")
public class DemoApplication {
    public static void main(final String... args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
