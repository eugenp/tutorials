package org.baeldung.ex.dataIntegrityviolationexception.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
@ComponentScan("org.baeldung.ex.dataIntegrityviolationexception.cause1")
public class Cause1DataContextWithJavaConfig {

    public Cause1DataContextWithJavaConfig() {
        super();
    }

    // beans

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}