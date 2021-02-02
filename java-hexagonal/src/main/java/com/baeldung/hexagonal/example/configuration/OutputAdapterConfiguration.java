package com.baeldung.hexagonal.example.configuration;

import com.baeldung.hexagonal.example.adapter.ReviewStoreInMemory;
import com.baeldung.hexagonal.example.port.output.ReviewStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutputAdapterConfiguration {
    @Bean
    public ReviewStore reviewStoreAdapter() {
        return new ReviewStoreInMemory();
    }
}
