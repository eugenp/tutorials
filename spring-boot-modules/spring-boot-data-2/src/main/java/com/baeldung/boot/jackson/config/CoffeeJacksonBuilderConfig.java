package com.baeldung.boot.jackson.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.baeldung.boot.jackson.config.CoffeeConstants.LOCAL_DATETIME_SERIALIZER;

@Configuration
public class CoffeeJacksonBuilderConfig {

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializers(LOCAL_DATETIME_SERIALIZER)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
}