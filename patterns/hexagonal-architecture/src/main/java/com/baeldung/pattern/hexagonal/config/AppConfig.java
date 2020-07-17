package com.baeldung.pattern.hexagonal.config;

import com.baeldung.pattern.hexagonal.domain.services.EmployeeService;
import com.baeldung.pattern.hexagonal.domain.services.EmployeeServiceImpl;
import com.baeldung.pattern.hexagonal.persistence.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public EmployeeService getEmployeeService(EmployeeRepository employeeRepository) {
        return new EmployeeServiceImpl(employeeRepository);
    }
}
