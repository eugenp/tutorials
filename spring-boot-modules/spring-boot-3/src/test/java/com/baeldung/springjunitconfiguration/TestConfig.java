package com.baeldung.springjunitconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Configuration
public class TestConfig {

    @Bean
    Person dilbert() {
        return new Person("Dilbert");
    }

    @Bean
    Person wally() {
        return new Person("Wally");
    }

}