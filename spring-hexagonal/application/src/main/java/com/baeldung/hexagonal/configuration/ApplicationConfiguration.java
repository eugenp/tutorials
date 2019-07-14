package com.baeldung.hexagonal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.domain.adapters.GetUserServiceAdapter;
import com.baeldung.hexagonal.domain.ports.GetUserService;
import com.baeldung.hexagonal.domain.ports.UserRepository;
import com.baeldung.hexagonal.repositoryadapters.UserRepositoryAdapter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public GetUserService getUserService(UserRepository userRepository) {
        return new GetUserServiceAdapter(userRepository);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryAdapter();
    }
}
