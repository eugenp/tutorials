package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.GreetingService;
import com.baeldung.di.WelcomeGreetingService;

@Configuration
@ComponentScan(basePackages={"com.baeldung.di"})
public class ApplicationContextTestConsturctorDI {
    @Bean
    public GreetingService createGreetingService() {
        return new WelcomeGreetingService();
    }
}
