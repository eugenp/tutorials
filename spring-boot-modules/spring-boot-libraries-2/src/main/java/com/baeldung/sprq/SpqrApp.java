package com.baeldung.sprq;

import org.jobrunr.autoconfigure.JobRunrAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { JobRunrAutoConfiguration.class})
public class SpqrApp {

    public static void main(String[] args) {
        SpringApplication.run(SpqrApp.class, args);
    }

}
