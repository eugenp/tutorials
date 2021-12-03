package com.baeldung.architecture.hexagonal.server.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("V1").select().apis(RequestHandlerSelectors.basePackage("com.baeldung.architecture.hexagonal.server.rest.adapter")).paths(PathSelectors.any()).build().apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Message REST API", "Sample Message.", "v1.0", "", null, "License of API", "API license URL", Collections.emptyList());
    }
}
