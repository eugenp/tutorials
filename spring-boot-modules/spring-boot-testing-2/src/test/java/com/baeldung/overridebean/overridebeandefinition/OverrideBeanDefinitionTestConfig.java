package com.baeldung.overridebean.overridebeandefinition;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.baeldung.overridebean.Service;

@TestConfiguration
public class OverrideBeanDefinitionTestConfig {

    @Bean
    public Service helloWorld() {
        return new OverrideBeanDefinitionServiceStub();
    }
}
