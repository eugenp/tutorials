package com.baeldung.spring.cloud.aws.sqs.acknowledgement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class OrderProcessingApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OrderProcessingApplication.class);
        app.setAdditionalProfiles("acknowledgement");
        app.run(args);
    }

}
