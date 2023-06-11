package com.baeldung.postman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PostmanUploadDemo {

    public static void main(String[] args) {
        SpringApplication.run(PostmanUploadDemo.class, args);
    }

}
