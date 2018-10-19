package org.baeldung.spring43.ctor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooServiceConfiguration {

    private final FooRepository repository;

    public FooServiceConfiguration(FooRepository repository) {
        this.repository = repository;
    }

    @Bean
    public FooService fooService() {
        return new FooService(this.repository);
    }
}
