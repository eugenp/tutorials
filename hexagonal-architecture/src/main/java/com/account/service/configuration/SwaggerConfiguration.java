package com.account.service.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@Configuration
@EnableSwagger2
class SwaggerConfiguration {

  @Bean
  public Docket serviceApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.account.service"))
      .paths(regex("/*.*"))
      .build()
      .apiInfo(appMetaData());
  }

  private ApiInfo appMetaData() {
    return new ApiInfoBuilder()
      .title("Account Service REST API")
      .description("Account Service")
      .version("1.0.0")
      .license("Any Licence")
      .contact(new Contact("Tugce Konuklar",
        "https://www.linkedin.com/in/tugce-konuklar/",
        "tkonuklar@gmail.com"))
      .build();
  }
}
