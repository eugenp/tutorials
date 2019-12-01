package com.baeldung.springfox;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.springfox.plugin.EmailAnnotationPlugin;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@SpringBootApplication
@EnableSwagger2WebMvc
@EntityScan("com.baeldung.springfox.model")
@ComponentScan("com.baeldung.springfox.controller")
@EnableJpaRepositories("com.baeldung.springfox.repository")
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SpringfoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringfoxApplication.class, args);
    }

    @Bean
    public Docket springfoxAppInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("baeldung-springfox-api")
          .select()
          .paths(paths())
          .build()
          .apiInfo(apiInfo());
    }

    private Predicate<String> paths() {
        return regex("/users.*").or(regex("/api.*"));       
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Springfox API specification", 
          "User REST and Spring Data APIs", 
          "", 
          "", 
          null, 
          "License of API", 
          "API license URL", 
          Collections.emptyList());
    }

    @Bean
    public EmailAnnotationPlugin emailPlugin() {
        return new EmailAnnotationPlugin();
    }

}
