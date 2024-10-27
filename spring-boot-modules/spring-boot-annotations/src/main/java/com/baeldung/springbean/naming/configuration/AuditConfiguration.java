package com.baeldung.springbean.naming.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.springbean.naming.service.AuditService;

@Configuration
public class AuditConfiguration {

    @Bean
    public AuditService audit() {
        return new AuditService();
    }
}
