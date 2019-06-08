package com.baeldung.controller.config;

import com.baeldung.adapter.CustomerRepository;
import com.baeldung.adapter.CustomerRepositoryAdapter;
import com.baeldung.domain.port.CustomerRepositoryPort;
import com.baeldung.domain.port.CustomerServicePort;
import com.baeldung.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    @Autowired
    CustomerServicePort customerService(CustomerRepositoryPort customerRepositoryPort) {
        return new CustomerService(customerRepositoryPort);
    }

    @Bean
    CustomerRepositoryPort customerAdapter() {
        return new CustomerRepositoryAdapter(new CustomerRepository());
    }
}
