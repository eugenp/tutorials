package com.baeldung.activiti.security.withspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.baeldung.activiti.security.config", "com.baeldung.activiti.security.withspring" })
public class SpringSecurityActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityActivitiApplication.class, args);
    }

}
