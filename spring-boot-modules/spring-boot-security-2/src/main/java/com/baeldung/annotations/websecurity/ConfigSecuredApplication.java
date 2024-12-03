package com.baeldung.annotations.websecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.annotations.websecurity")
public class ConfigSecuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigSecuredApplication.class, args);
    }
}
