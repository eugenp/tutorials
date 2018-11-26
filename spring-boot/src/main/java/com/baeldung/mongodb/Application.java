package com.baeldung.mongodb;

import com.baeldung.mongodb.daos.UserRepository;
import com.baeldung.mongodb.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {

            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("john.doe@example.com");
            userRepository.save(user);

            List<User> storedUsers = userRepository.findAll();
            storedUsers.forEach(System.out::println);

        };
    }
}
