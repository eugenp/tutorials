package com.baeldung.app;

import com.baeldung.adapter.service.UserServiceAdapter;
import com.baeldung.domain.User;
import com.baeldung.port.primary.RestServicesEndPoint;
import com.baeldung.port.secondary.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@ComponentScan(basePackageClasses = {RestServicesEndPoint.class, UserServiceAdapter.class})
@SpringBootApplication
public class HexagonalArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User(1, "User 1", "user1", "secret1", "user1@exampole.com", "987654321"));
            userRepository.save(new User(2, "User 2", "user2", "secret2", "user2@exampole.com", "987654321"));
            userRepository.save(new User(3, "User 3", "user3", "secret3", "user3@exampole.com", "987654321"));
            userRepository.save(new User(4, "User 4", "user4", "secret4", "user4@exampole.com", "987654321"));
            userRepository.save(new User(5, "User 5", "user5", "secret5", "user5@exampole.com", "987654321"));
        };
    }
}
