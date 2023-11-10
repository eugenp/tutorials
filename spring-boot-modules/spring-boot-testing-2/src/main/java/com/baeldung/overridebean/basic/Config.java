package com.baeldung.overridebean.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.overridebean.Service;
import com.baeldung.overridebean.ServiceImpl;

@Configuration
public class Config {

    @Bean
    public Service helloWorld() {
        return new ServiceImpl();
    }
}
