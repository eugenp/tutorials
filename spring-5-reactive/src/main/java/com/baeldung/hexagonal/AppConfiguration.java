package com.baeldung.hexagonal;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;
import com.baeldung.hexagonal.core.ports.ExchangeRateRepository;
import com.baeldung.hexagonal.core.ports.ExchangeRateRetrievalService;
import com.baeldung.hexagonal.core.service.ExchangeRateService;
import com.baeldung.hexagonal.core.service.ExchangeRateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.com.baeldung.hexagonal")
public class AppConfiguration {

    @Bean
    public ExchangeRateService exchangeRateService(ExchangeRateRepository<ExchangeRateDomain> repository,
                                                   ExchangeRateRetrievalService retrievalService) {
        return new ExchangeRateServiceImpl(retrievalService, repository);
    }
}
