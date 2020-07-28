package com.baeldung.architecture.hexagonal.repositories.configuration;

import com.baeldung.architecture.hexagonal.QuotesApplication;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;
import com.baeldung.architecture.hexagonal.domain.services.QuoteService;
import com.baeldung.architecture.hexagonal.domain.services.QuoteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = QuotesApplication.class)
public class BeanConfiguration {

    @Bean
    QuoteService quoteService(QuoteRepository quoteRepository) {
        return new QuoteServiceImpl(quoteRepository);
    }
}
