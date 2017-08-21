package com.baeldung.beaninjection.constructorbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.model.Currency;
import com.baeldung.beaninjection.model.JapaneseYen;
import com.baeldung.beaninjection.model.Transaction;

/**
 * Created by banks on 020 Aug 20 2017 9:01 PM.
 */
@Configuration
public class ConstructorBasedAppConfig {

    @Bean
    public Currency currency() {
        return new JapaneseYen();
    }

    @Bean
    public Transaction transaction() {
        return new Transaction(currency());
    }
}
