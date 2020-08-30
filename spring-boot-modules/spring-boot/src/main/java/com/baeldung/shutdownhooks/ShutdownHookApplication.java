package com.baeldung.shutdownhooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ShutdownHookApplication {

    public static void main(String args[]) {
        SpringApplication.run(ShutdownHookApplication.class, args);
    }
}
