package com.baeldung.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:default-application.properties")
@SpringBootApplication
public class SpringOAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOAuthApplication.class, args);
    }

}
