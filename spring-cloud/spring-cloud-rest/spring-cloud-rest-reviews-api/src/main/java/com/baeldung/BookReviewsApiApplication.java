package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookReviewsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReviewsApiApplication.class, args);
    }
}
