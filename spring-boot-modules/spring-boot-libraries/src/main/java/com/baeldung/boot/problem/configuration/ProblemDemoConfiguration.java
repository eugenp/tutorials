package com.baeldung.boot.problem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ProblemDemoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        // In this example, stack traces support is enabled by default. 
        // If you want to disable stack traces just use new ProblemModule() instead of new ProblemModule().withStackTraces()
        return new ObjectMapper().registerModules(new ProblemModule().withStackTraces(), new ConstraintViolationProblemModule());
    }
}
