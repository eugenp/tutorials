package com.baeldung.gracefulshutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GracefulShutdownApplication {

    public static void main(String args[]) {
        SpringApplication.run(GracefulShutdownApplication.class, args);
    }
}
