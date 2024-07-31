package com.baeldung.securityfilterchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SecurityFilterChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityFilterChainApplication.class, args);
    }
}
