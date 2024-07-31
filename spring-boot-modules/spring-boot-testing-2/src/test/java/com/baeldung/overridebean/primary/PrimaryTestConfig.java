package com.baeldung.overridebean.primary;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.baeldung.overridebean.Service;

@TestConfiguration
public class PrimaryTestConfig {

    @Primary
    @Bean("service.stub")
    public Service helloWorld() {
        return new PrimaryServiceStub();
    }
}
