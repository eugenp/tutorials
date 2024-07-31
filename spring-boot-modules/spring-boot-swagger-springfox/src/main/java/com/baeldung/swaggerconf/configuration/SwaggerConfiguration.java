package com.baeldung.swaggerconf.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {
        return new ApiInfo("My REST API", "Some custom description of API.", "API TOS", "Terms of service",
          new Contact("General UserName", "www.baeldung.com", "user-name@gmail.com"),
          "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.baeldung.swaggerconf.controller"))
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
          .paths(regex("/good-path/.*"))
          .build();
    }

}
