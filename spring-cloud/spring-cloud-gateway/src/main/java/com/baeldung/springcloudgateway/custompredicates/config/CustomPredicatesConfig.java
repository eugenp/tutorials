package com.baeldung.springcloudgateway.custompredicates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.springcloudgateway.custompredicates.factories.GoldenCustomerPredicateFactory;

@Configuration
public class CustomPredicatesConfig {
    
    
    @Bean
    public GoldenCustomerPredicateFactory goldenCustomerPredicateFactory() {
        return new GoldenCustomerPredicateFactory();
    }
    

}
