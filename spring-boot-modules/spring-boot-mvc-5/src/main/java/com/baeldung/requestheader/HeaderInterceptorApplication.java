package com.baeldung.requestheader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class HeaderInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeaderInterceptorApplication.class, args);
    }

}
