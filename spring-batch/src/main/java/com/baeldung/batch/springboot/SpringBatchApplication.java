package com.baeldung.batch.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(SpringBatchApplication.class);
        springApp.setAdditionalProfiles("spring-boot");
        springApp.run(args);
    }

}
