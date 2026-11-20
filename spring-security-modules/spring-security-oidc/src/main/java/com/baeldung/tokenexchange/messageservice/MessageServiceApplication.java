package com.baeldung.tokenexchange.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MessageServiceApplication.class);
        application.setAdditionalProfiles("messageservice");
        application.run(args);
    }
}
