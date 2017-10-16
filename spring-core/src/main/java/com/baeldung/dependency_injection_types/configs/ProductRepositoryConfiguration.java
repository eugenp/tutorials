package com.baeldung.dependency_injection_types.configs;

import com.baeldung.dependency_injection_types.repositories.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.dependency_injection_types")
public class ProductRepositoryConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }
}
