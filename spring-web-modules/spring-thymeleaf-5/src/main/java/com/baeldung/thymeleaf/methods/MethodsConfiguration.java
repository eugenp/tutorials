package com.baeldung.thymeleaf.methods;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MethodsConfiguration {
    @Bean
    public MethodsBean methodsBean() {
        return new MethodsBean();
    }

    public static class MethodsBean {
        public String hello() {
            return "Hello, Baeldung!";
        }
    }
}
