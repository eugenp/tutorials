package com.baeldung.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityLoggingApplication {

    public static void main(String... args) {
        SpringApplication application = new SpringApplication(SecurityLoggingApplication.class);
        application.setAdditionalProfiles("logging");
        application.run(args);
    }
}
