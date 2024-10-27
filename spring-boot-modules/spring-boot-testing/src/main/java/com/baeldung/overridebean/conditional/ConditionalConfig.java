package com.baeldung.overridebean.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.overridebean.Service;
import com.baeldung.overridebean.ServiceImpl;

@Configuration
public class ConditionalConfig {

    @Bean
    @ConditionalOnProperty(name = "service.stub", havingValue = "false")
    public Service helloWorld() {
        return new ServiceImpl();
    }
}
