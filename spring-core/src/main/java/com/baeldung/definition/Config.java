package com.baeldung.definition;

import com.baeldung.definition.domain.Account;
import com.baeldung.definition.domain.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ComponentScan("com.baeldung.definition.domain")
public class Config {
    @Bean
    public Address getAddress() {
        return new Address("High Street", 1000);
    }

    @Bean("account")
    public Account getAccount() {
        return new Account(1, BigDecimal.valueOf(1000.00));
    }
}
