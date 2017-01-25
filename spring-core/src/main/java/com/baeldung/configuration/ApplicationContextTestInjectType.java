package com.baeldung.configuration;

import com.baeldung.dependency.ArbitraryDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestInjectType {

    @Bean
    public ArbitraryDependency injectDependency() {
        ArbitraryDependency injectDependency = new ArbitraryDependency();
        return injectDependency;
    }
}
