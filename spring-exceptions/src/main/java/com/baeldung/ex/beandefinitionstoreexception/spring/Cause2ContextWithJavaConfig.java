package com.baeldung.ex.beandefinitionstoreexception.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.baeldung.ex.beandefinitionstoreexception.cause2")
public class Cause2ContextWithJavaConfig {

    @Value("${some.property}")
    private String someProperty;

    public Cause2ContextWithJavaConfig() {
        super();
    }

    // beans

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}