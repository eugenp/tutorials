package com.baeldung.spring.jdbc.batch.config;

import com.baeldung.spring.jdbc.batch.repo.BatchProductRepository;
import com.baeldung.spring.jdbc.batch.repo.SimpleProductRepository;
import com.baeldung.spring.jdbc.batch.service.ProductService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Clock;
import java.util.Random;

@Configuration
@PropertySource("classpath:com/baeldung/spring/jdbc/batch/application.properties")
public class AppConfig {

    @Bean
    public ProductService simpleProductService(SimpleProductRepository simpleProductRepository) {
        return new ProductService(simpleProductRepository, new Random(), Clock.systemUTC());
    }

    @Bean
    public ProductService batchProductService(BatchProductRepository batchProductRepository) {
        return new ProductService(batchProductRepository, new Random(), Clock.systemUTC());
    }
}
