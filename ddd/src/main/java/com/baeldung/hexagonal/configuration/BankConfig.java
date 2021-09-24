package com.baeldung.hexagonal.configuration;

import com.baeldung.hexagonal.adapter.AccountJpaAdapter;
import com.baeldung.hexagonal.domain.AccountServiceImpl;
import com.baeldung.hexagonal.ports.api.AccountServicePort;
import com.baeldung.hexagonal.ports.spi.AccountPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {

    @Bean
    public AccountPersistencePort accountPersistence() {
        return new AccountJpaAdapter();
    }

    @Bean
    public AccountServicePort bookService() {
        return new AccountServiceImpl(accountPersistence());
    }
}
