package com.baeldung.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.app.domain.ports.UserRepository;
import com.baeldung.app.domain.ports.UserService;
import com.baeldung.app.inmemory.adapters.UserRepositoryAdapter;
import com.baeldung.app.domain.adapters.UserServiceAdapter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceAdapter(userRepository);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryAdapter();
    }
}
