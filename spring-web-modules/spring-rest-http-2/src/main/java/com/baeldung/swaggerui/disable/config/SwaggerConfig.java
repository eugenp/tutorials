package com.baeldung.swaggerui.disable.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Profile("!prod && swagger")
//@Profile("!prod")
// @Profile("swagger")
// @ConditionalOnExpression(value = "${useSwagger:false}")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("SpringDoc Disable SwaggerUI example")
            .description("SpringDoc Disable SwaggerUI application")
            .version("v0.0.1"));
    }
}
