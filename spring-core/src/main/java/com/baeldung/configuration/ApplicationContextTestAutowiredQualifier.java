package com.baeldung.configuration;

import com.baeldung.dependency.AnotherArbitraryDependency;
import com.baeldung.dependency.ArbitraryDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestAutowiredQualifier {

    @Bean
    public ArbitraryDependency autowiredFieldDependency() {
        ArbitraryDependency autowiredFieldDependency = new ArbitraryDependency();

        return autowiredFieldDependency;
    }

    @Bean
    public ArbitraryDependency anotherAutowiredFieldDependency() {
        ArbitraryDependency anotherAutowiredFieldDependency = new AnotherArbitraryDependency();

        return anotherAutowiredFieldDependency;
    }
}
