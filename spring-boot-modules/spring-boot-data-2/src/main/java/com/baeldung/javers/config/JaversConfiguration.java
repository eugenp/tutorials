package com.baeldung.javers.config;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaversConfiguration {
    @Bean
    public AuthorProvider provideJaversAuthor() {
        return new SimpleAuthorProvider();
    }

    private static class SimpleAuthorProvider implements AuthorProvider {
        @Override
        public String provide() {
            return "Baeldung Author";
        }
    }
}
