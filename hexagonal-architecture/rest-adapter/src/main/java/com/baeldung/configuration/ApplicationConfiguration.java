package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.inmemory.UserRepositoryImpl;
import lombok.AllArgsConstructor;
import com.baeldung.port.UserService;
import com.baeldung.service.UserServiceImpl;

@Configuration
@AllArgsConstructor
@ComponentScan
public class ApplicationConfiguration {

    private UserRepositoryImpl inMemoryUserRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(inMemoryUserRepository());
    }
}
