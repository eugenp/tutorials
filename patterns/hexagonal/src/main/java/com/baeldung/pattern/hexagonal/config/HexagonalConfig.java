package com.baeldung.pattern.hexagonal.config;

import com.baeldung.pattern.hexagonal.domain.service.UserService;
import com.baeldung.pattern.hexagonal.domain.service.UserServiceImpl;
import com.baeldung.pattern.hexagonal.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexagonalConfig {

    @Bean
    public UserService getUserService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
}
