package com.baeldung.springdatawebsupport.application;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.springdatawebsupport.application.entities.User;
import com.baeldung.springdatawebsupport.application.repositories.UserRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initialize(UserRepository userRepository) {
        return args -> {
            Stream.of("John", "Robert", "Nataly", "Helen", "Mary").forEach(name -> {
                User user = new User(name);
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
        };
    }
}
