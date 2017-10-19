package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectionTypesConfig {

    @Bean
    public Dependency dependency() {
        return new Dependency();
    }

    @Bean
    public ServiceThroughConstructor serviceThroughConstructor(Dependency dependency) {
        return new ServiceThroughConstructor(dependency);
    }

    @Bean
    public ServiceThroughSetter service(Dependency dependency) {
        ServiceThroughSetter result = new ServiceThroughSetter();
        result.setDependency(dependency);
        return result;
    }
}
