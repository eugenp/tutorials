package com.baeldung.multibeaninstantiation.solution3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.multiple_bean_instantiation.solution3")
public class PersonConfig {
    @Bean
    public PersonFactoryPostProcessor PersonFactoryPostProcessor() {
        return new PersonFactoryPostProcessor();
    }

    @Bean
    public Human getHuman() {
        return new Human();
    }
}