package com.baeldung.registrypostprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
public class RegistryPostProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryPostProcessorApplication.class, args);
    }

    @Bean
    public ApiClientConfiguration apiClientConfiguration(ConfigurableEnvironment environment) {
        return new ApiClientConfiguration(environment);
    }

}

