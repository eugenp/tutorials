package com.baeldung.spring.jinq.config;

import jakarta.persistence.EntityManagerFactory;

import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JinqProviderConfiguration {

    @Bean
    @Autowired
    JinqJPAStreamProvider jinqProvider(EntityManagerFactory emf) {
        return new JinqJPAStreamProvider(emf);
    }
}
