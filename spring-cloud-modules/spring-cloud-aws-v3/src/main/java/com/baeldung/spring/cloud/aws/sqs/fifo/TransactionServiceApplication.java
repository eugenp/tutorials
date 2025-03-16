package com.baeldung.spring.cloud.aws.sqs.fifo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TransactionServiceApplication.class);
        app.setAdditionalProfiles("fifo");
        app.run(args);
    }

}
