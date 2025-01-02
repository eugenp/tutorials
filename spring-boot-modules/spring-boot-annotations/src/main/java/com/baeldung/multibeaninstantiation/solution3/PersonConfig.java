package com.baeldung.multibeaninstantiation.solution3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {
    @Bean
    public PersonFactoryPostProcessor PersonFactoryPostProcessor() {
        return new PersonFactoryPostProcessor();
    }

    @Bean
    public Person person() {
        return new Person();
    }

    @Bean
    public Human human() {
        return new Human();
    }
}