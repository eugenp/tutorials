package com.baeldung.adapters.configuration;

import com.baeldung.HexagonalArchitectureApplication;
import com.baeldung.adapters.persistence.GamerAccountRepository;
import com.baeldung.application.service.GamerAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HexagonalArchitectureApplication.class)
public class BeanConfiguration {

    @Bean
    GamerAccountService gamerAccountService(GamerAccountRepository repository) {
        return new GamerAccountService(repository, repository);
    }

}
