package com.baeldung.di.types;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("constructor-inject")
@Configuration
public class ConstructorInjectConfiguration {

    @Bean
    public Formatter javaFormatter() {
        return new JavaFormatter();
    }
    
    @Bean
    public CodeEditor codeEditor() {
        return new CodeEditor(javaFormatter());
    }
}
