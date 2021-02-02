package com.baeldung.hexagonal.example.integration;

import com.baeldung.hexagonal.example.configuration.DomainUseCaseConfiguration;
import com.baeldung.hexagonal.example.configuration.InputAdapterConfiguration;
import com.baeldung.hexagonal.example.port.output.ReviewStore;
import com.baeldung.hexagonal.example.test.helper.MockDatabaseAdapter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({
        InputAdapterConfiguration.class,
        DomainUseCaseConfiguration.class
})
@EnableAutoConfiguration
@TestConfiguration
class IntegrationTestConfiguration {
    @Bean
    public ReviewStore mockDB() {
        return new MockDatabaseAdapter();
    }
}
