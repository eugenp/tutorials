package com.baeldung.beaninjection.setterbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.model.Currency;
import com.baeldung.beaninjection.model.IndianRupee;
import com.baeldung.beaninjection.model.Transaction;

@Configuration
public class SetterBasedAppConfig {

    @Bean
    public Currency currency() {
        return new IndianRupee();
    }

    @Bean
    public Transaction transaction() {
        Transaction transaction = new Transaction();
        transaction.setCurrency(currency());
        return transaction;
    }
}