package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.port.UserService;
import com.baeldung.service.UserServiceImpl;


@Configuration
public class UserServiceConfiguration {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
