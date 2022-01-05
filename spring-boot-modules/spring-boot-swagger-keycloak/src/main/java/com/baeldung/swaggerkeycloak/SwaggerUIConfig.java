package com.baeldung.swaggerkeycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableOpenApi
@Configuration
class SwaggerUIConfig {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.OAS_30)
          .useDefaultResponseMessages(false)
          .select()
          .apis(basePackage(TodosApplication.class.getPackage().getName()))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Todos Management Service")
          .description("A service providing todos.")
          .version("1.0")
          .build();
    }

}
