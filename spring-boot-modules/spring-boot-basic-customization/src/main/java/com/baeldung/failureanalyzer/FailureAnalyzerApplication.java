package com.baeldung.failureanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import javax.annotation.security.RolesAllowed;

@Profile("failureanalyzer")
@SpringBootApplication(scanBasePackages = "com.baeldung.failureanalyzer")
public class FailureAnalyzerApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(FailureAnalyzerApplication.class, args);
    }
}
