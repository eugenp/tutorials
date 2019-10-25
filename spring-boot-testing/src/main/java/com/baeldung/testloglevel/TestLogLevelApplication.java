package com.baeldung.testloglevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.boot.Application;

@SpringBootApplication(scanBasePackages = {"com.baeldung.testloglevel", "com.baeldung.component"})
public class TestLogLevelApplication {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
