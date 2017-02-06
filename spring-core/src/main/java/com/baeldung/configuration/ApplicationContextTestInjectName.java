package com.baeldung.configuration;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.dependency.YetAnotherArbitraryDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestInjectName {

    @Bean
    public ArbitraryDependency yetAnotherFieldInjectDependency() {
        ArbitraryDependency yetAnotherFieldInjectDependency = new YetAnotherArbitraryDependency();
        return yetAnotherFieldInjectDependency;
    }
}
