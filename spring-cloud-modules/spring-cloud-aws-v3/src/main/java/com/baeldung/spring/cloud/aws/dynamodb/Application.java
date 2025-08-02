package com.baeldung.spring.cloud.aws.dynamodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setAdditionalProfiles("dynamodb");
        springApplication.run(args);
    }

}