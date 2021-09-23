package com.hexagonal.configuration;

import com.hexagonal.adapter.AccountJpaAdapter;
import com.hexagonal.domain.AccountServiceImpl;
import com.hexagonal.ports.api.AccountServicePort;
import com.hexagonal.ports.spi.AccountPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {

    @Bean
    public AccountPersistencePort accountPersistence(){
        return new AccountJpaAdapter();
    }

    @Bean
    public AccountServicePort bookService(){
        return new AccountServiceImpl(accountPersistence());
    }
}
