package com.baeldung.hexagonal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import com.baeldung.hexagonal.core.ports.ExchangeRateRepository;
import com.baeldung.hexagonal.core.ports.ExchangeRateRetrievalService;
import com.baeldung.hexagonal.core.service.ExchangeRateService;
import com.baeldung.hexagonal.core.service.ExchangeRateServiceImpl;

@Configuration
@ComponentScan("com.baeldung.com.baeldung.hexagonal")
public class AppConfiguration {

    @Bean
    public ExchangeRateService exchangeRateService(ExchangeRateRepository<ExchangeRate> repository, ExchangeRateRetrievalService retrievalService) {
        return new ExchangeRateServiceImpl(retrievalService, repository);
    }
}
