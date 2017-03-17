package com.baeldung.injection.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injection.consumer.IAccountTransaction;
import com.baeldung.injection.service.AccountToAccountTransfer;

@Configuration
@ComponentScan(value = { "com.baeldung.injection" })
public class AccountConfiguration {
    @Bean
    public IAccountTransaction getAccountService() {
        return new AccountToAccountTransfer();
    }

}
