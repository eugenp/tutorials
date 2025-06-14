package com.baeldung.resttemplate;

import com.baeldung.resttemplate.web.handler.RestTemplateResponseErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
public class RestTemplateConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateConfigurationApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }
}
