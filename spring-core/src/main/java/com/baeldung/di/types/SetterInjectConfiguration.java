package com.baeldung.di.types;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("setter-inject")
@Configuration
public class SetterInjectConfiguration {

    @Bean
    public Formatter javaFormatter() {
        return new JavaFormatter();
    }
    
    @Bean
    public CodeEditor codeEditor() {
        CodeEditor editor = new CodeEditor();
        editor.setFormatter(javaFormatter());
        
        return editor;
    }
}
