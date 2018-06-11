package com.baeldung.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.services.IHomeService;

@Configuration
@ComponentScan("com.baeldung.web")
@EnableAutoConfiguration
@PropertySource("classpath:web-app.properties")
public class WebConfig {
    
    @Bean
    public IHomeService homeService() {
        return new GreetingService();
    }
}
