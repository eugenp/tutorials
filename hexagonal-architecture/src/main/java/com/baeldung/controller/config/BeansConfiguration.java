package com.baeldung.controller.config;

import com.baeldung.adapter.CustomerAdapter;
import com.baeldung.adapter.CustomerRepository;
import com.baeldung.domain.port.CustomerPort;
import com.baeldung.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.baeldung.adapter")
@EntityScan("com.baeldung.domain.entity")
public class BeansConfiguration {

    @Bean
    @Autowired
    CustomerService customerService(CustomerPort customerPort) {
        return new CustomerService(customerPort);
    }

    @Bean
    @Autowired
    CustomerAdapter customerAdapter(CustomerRepository customerRepository) {
        return new CustomerAdapter(customerRepository);
    }
}
