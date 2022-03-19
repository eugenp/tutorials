package com.baeldung.hexagonal.architecture.example.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String REGEX_API = "/api.+";

    private static ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfo(title, description, version, null, null, null, null, new ArrayList<>());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Bank API")
            .apiInfo(apiInfo("Banking API - Hexagonal Architecture Example", "API documentation for backend module of Banking Project", "1.0"))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex(REGEX_API))
            .build();
    }
}