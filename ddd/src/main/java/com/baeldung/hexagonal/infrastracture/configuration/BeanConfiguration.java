package com.baeldung.hexagonal.infrastracture.configuration;

import com.baeldung.hexagonal.domain.repository.SalaryRepository;
import com.baeldung.hexagonal.domain.service.DomainSalaryService;
import com.baeldung.hexagonal.domain.service.SalaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    SalaryService salaryService(SalaryRepository salaryRepository) {
        return  new DomainSalaryService(salaryRepository);
    }
}
