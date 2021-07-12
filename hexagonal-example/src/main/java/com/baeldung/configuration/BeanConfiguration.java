package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.Application;
import com.baeldung.domain.service.AccountService;
import com.baeldung.infrastructure.adapter.AccountRepository;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class BeanConfiguration {

    @Bean
    AccountService bankAccountService(AccountRepository repository) {
        return new AccountService(repository, repository);
    }

}
