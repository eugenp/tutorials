package com.baeldung.swagger2boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.baeldung.swagger2boot.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build()
            .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Swagger Array")
            .description("This is a sample Swagger description for an Array server")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
}