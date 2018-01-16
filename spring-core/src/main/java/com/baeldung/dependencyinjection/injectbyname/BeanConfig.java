package com.baeldung.dependencyinjection.injectbyname;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependencyinjection.Dictionary;

@Configuration
@ComponentScan
public class BeanConfig {

    @Bean
    public Dictionary englishDictionary() {
        return new Dictionary("English");
    }

    @Bean
    public Dictionary frenchDictionary() {
        return new Dictionary("French");
    }
}
