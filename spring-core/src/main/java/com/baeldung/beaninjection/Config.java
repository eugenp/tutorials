package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.domain.Engine;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine();
    }
}
