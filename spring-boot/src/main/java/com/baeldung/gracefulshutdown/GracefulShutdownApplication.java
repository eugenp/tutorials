package com.baeldung.gracefulshutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.baeldung.gracefulshutdown"})
public class GracefulShutdownApplication {

    public static void main(String args[]) {
        SpringApplication.run(GracefulShutdownApplication.class, args);
    }
}
