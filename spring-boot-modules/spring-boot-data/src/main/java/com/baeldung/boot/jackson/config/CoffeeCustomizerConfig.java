package com.baeldung.boot.jackson.config;

import static com.baeldung.boot.jackson.config.CoffeeConstants.LOCAL_DATETIME_SERIALIZER;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

@Configuration
public class CoffeeCustomizerConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializers(LOCAL_DATETIME_SERIALIZER);
    }
}
