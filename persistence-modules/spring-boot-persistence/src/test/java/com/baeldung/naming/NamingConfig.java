package com.baeldung.naming;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

public class NamingConfig {
    @TestConfiguration
    static class Config {
        @Bean
        public HibernatePropertiesCustomizer customizer() {
            return new HibernateConfig();
        }
    }
}
