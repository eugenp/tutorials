package com.baeldung.wiring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependency.ArbitraryDependency;

@Configuration
public class ApplicationContextTestInjectType {

    @Bean
    public ArbitraryDependency injectDependency() {
        ArbitraryDependency injectDependency = new ArbitraryDependency();
        return injectDependency;
    }
}
