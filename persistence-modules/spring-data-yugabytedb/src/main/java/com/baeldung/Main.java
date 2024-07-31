package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {

        int iterationCount = 1_000;
        int elementsPerIteration = 100;

        for (int i = 0; i < iterationCount; i++) {
            for (long j = 0; j < elementsPerIteration; j++) {
                User user = new User();
                userRepository.save(user);
            }
            Thread.sleep(1000);
        }
    }
}
