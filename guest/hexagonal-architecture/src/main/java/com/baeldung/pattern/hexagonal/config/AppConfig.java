package com.baeldung.pattern.hexagonal.config;

import com.baeldung.pattern.hexagonal.product.adapter.outbound.persistence.InMemoryProductAdapter;
import com.baeldung.pattern.hexagonal.product.application.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProductService productService() {
        InMemoryProductAdapter inMemoryProductAdapter = new InMemoryProductAdapter();
        return new ProductService(inMemoryProductAdapter);
    }
}
