package com.baeldung.enablemethodsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class EnableMethodSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnableMethodSecurityApplication.class, args);
    }
}
