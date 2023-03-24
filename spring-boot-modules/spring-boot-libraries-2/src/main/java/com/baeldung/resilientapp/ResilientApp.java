package com.baeldung.resilientapp;

import org.jobrunr.autoconfigure.JobRunrAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { JobRunrAutoConfiguration.class})
public class ResilientApp {

    public static void main(String[] args) {
        SpringApplication.run(ResilientApp.class, args);
    }

}
