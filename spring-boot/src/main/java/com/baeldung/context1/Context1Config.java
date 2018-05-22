package com.baeldung.context1;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.contexts.IHomeService;

@Configuration
@ComponentScan("com.baeldung.contexts.context1")
@EnableAutoConfiguration
@PropertySource("classpath:ctx1.properties")
public class Context1Config {
    
    @Bean
    public IHomeService homeService() {
        return new GreetingsService();
    }
}