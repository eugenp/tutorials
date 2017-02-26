package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.domain.Transaction;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class BeanInjectionConfig {

    @Bean
    public Transaction transaction() {
        return new Transaction("deposit");
    }
}
