package com.baeldung.injection.withinjection.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injection.withinjection.consumer.AccountTransaction;
import com.baeldung.injection.withinjection.service.AccountToAccountTransfer;

@Configuration
@ComponentScan(value = { "com.baeldung.injection.withinjection" })
public class AccountConfiguration {
    @Bean
    public AccountTransaction getAccountService() {
        return new AccountToAccountTransfer();
    }

}
