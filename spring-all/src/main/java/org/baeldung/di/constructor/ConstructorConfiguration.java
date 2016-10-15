package org.baeldung.di.constructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorConfiguration {

    @Bean
    public SimpleDocumentParser simpleDocumentParser() {
        return new SimpleDocumentParser(documentReader());
    }

    private DocumentReader documentReader() {
        return new DocumentReader();
    }
}
