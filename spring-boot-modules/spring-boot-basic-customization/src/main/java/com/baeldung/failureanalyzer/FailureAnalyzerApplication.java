package com.baeldung.failureanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.security.RolesAllowed;

@SpringBootApplication
public class FailureAnalyzerApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(FailureAnalyzerApplication.class, args);
    }
}
