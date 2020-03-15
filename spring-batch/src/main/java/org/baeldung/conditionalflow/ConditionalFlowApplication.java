package org.baeldung.conditionalflow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConditionalFlowApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConditionalFlowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running and exiting");
    }
}
