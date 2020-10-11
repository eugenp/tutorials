package com.baeldung.hexarch.adapters.configuration;

import com.baeldung.hexarch.HexagonalApplication;
import com.baeldung.hexarch.application.services.CardService;
import com.baeldung.hexarch.adapters.persistence.CardImplRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HexagonalApplication.class)
public class BeanConfig {

    @Bean
    CardService cardService(CardImplRepo repository) {
        return new CardService(repository, repository);
    }
}
