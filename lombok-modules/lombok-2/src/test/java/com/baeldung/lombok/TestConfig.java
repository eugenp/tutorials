package com.baeldung.lombok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan("com.baeldung.lombok")
class TestConfig {

    @Bean
    public Translator mockTranslator() {
        return mock(Translator.class);
    }
}
