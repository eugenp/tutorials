package com.baeldung.pattern.hexagonal.config;

import com.baeldung.pattern.hexagonal.domain.services.CustomerService;
import com.baeldung.pattern.hexagonal.domain.services.CustomerServiceImpl;
import com.baeldung.pattern.hexagonal.persistence.CustomerRepository;
import com.baeldung.pattern.hexagonal.persistence.AddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CustomerService getCustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        return new CustomerServiceImpl(customerRepository, addressRepository);
    }
}
