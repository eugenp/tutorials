package com.baeldung.schedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}