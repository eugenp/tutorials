package com.hexagonal.arch.examples.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hexagonal.arch.examples.service.CustomerCreationService;

@Configuration
@EnableWebMvc

public class CustomerConfig {
    @Bean
    public CustomerCreationAdapter customerCreationAdapter() {
        return new CustomerCreationAdapter();
    }
    @Bean
    public CustomerCreationService customerCreationService() {
        return new CustomerCreationService();
    }
}
