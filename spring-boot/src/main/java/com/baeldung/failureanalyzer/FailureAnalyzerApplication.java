package com.baeldung.failureanalyzer;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FailureAnalyzerApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(FailureAnalyzerApplication.class, args);
    }
}
