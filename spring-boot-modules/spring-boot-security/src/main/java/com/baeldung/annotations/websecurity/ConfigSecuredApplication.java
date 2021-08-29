package com.baeldung.annotations.websecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ConfigSecuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigSecuredApplication.class, args);
    }
}
