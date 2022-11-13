package com.baeldung.basicauth.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    private static final String BASIC_AUTH_SCHEMA = "basicAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SCHEMA, createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(BASIC_AUTH_SCHEMA));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(BASIC_AUTH_SCHEMA)
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic");
    }

}
