package com.baeldung.wiring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.dependency.YetAnotherArbitraryDependency;

@Configuration
public class ApplicationContextTestInjectName {

    @Bean
    public ArbitraryDependency yetAnotherFieldInjectDependency() {
        ArbitraryDependency yetAnotherFieldInjectDependency = new YetAnotherArbitraryDependency();
        return yetAnotherFieldInjectDependency;
    }
}
