package com.baeldung.hexagonal.adapters.configuration;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.adapters.persistence.BankAccountRepository;
import com.baeldung.hexagonal.application.services.BankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HexagonalApplication.class)
public class BeanConfiguration {

    @Bean
    BankAccountService bankAccountService(BankAccountRepository repository) {
        return new BankAccountService(repository, repository);
    }
}
