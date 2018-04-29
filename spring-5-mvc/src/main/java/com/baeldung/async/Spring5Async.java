package com.baeldung.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Spring5Async {
    
    public static void main(String[] args) {
        SpringApplication.run(Spring5Async.class, args);
    }

}
