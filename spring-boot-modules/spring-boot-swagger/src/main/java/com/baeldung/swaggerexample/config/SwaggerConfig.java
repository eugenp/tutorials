package com.baeldung.swaggerexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.baeldung.swaggerexample"))
          .paths(PathSelectors.any())
          .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Products API",
          "API to let you add and view product",
          "0.0.1",
          "Terms of service",
          new Contact("John Doe", "www.example.com", "myemail@company.com"),
          "License of API", "API license URL", Collections.emptyList());
    }
}
