package com.baeldung.tokenexchange.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UserServiceApplication.class);
        application.setAdditionalProfiles("userservice");
        application.run(args);
    }

}
