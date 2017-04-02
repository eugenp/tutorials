package com.baeldung.spring.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.di.constructor.model.Deal;
import com.baeldung.spring.di.constructor.model.Stock;

@Configuration
@ComponentScan("com.baeldung.spring.di")
public class Config {

    @Bean
    public Deal deal() {
        return new Deal(2.0, 1200);
    }

    @Bean
    public Stock stock() {
        return new Stock("Apple", "Equity");
    }

}
