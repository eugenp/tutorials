package com.baeldung.architecture.hexagonal.config;

import com.baeldung.architecture.hexagonal.user.core.UserFacade;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.AddUser;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.RemoveUser;
import com.baeldung.architecture.hexagonal.user.core.ports.outgoing.UserRepository;
import com.baeldung.architecture.hexagonal.user.infrastructure.UserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDomainConfig {

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryAdapter();
    }

    @Bean
    public AddUser addUser(UserRepository userRepository) {
        return new UserFacade(userRepository);
    }

    @Bean
    public RemoveUser removeUser(UserRepository userRepository) {
        return new UserFacade(userRepository);
    }
}
