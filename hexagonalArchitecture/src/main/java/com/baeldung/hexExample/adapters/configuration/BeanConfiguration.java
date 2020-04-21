package com.baeldung.hexExample.adapters.configuration;

import com.baeldung.hexExample.MainDomainRunner;
import com.baeldung.hexExample.adapters.persistance.BankAccountRepository;
import com.baeldung.hexExample.application.services.BankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MainDomainRunner.class)
public class BeanConfiguration {

    @Bean
    BankAccountService bankAccountService(BankAccountRepository repository) {
        return new BankAccountService(repository, repository);
    }
}