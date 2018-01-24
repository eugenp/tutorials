package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LanguageService {

    @Bean
    public IProgrammingLanguage getFavoriteLanguage() {
        return new Java();
    }
}
