package com.baeldung.springcloudgateway.custompredicates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.springcloudgateway.custompredicates.factories.GoldenCustomerPredicateFactory;
import com.baeldung.springcloudgateway.custompredicates.service.GoldenCustomerService;

@Configuration
public class CustomPredicatesConfig {
    
    
    @Bean
    public GoldenCustomerPredicateFactory goldenCustomer(GoldenCustomerService goldenCustomerService) {
        return new GoldenCustomerPredicateFactory(goldenCustomerService);
    }
    

}
