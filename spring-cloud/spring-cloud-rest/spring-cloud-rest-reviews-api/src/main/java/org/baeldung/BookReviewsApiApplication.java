package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
class BookReviewsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReviewsApiApplication.class, args);
    }
}
