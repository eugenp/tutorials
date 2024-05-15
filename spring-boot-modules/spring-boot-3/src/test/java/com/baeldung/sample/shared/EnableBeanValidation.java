package com.baeldung.sample.shared;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@TestConfiguration
public class EnableBeanValidation {

    @Bean
    public MethodValidationPostProcessor validator() {
        return new MethodValidationPostProcessor();
    }

}
