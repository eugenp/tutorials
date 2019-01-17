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
        return new ObjectMapper().registerModules(new ProblemModule(), new ConstraintViolationProblemModule());
    }
}
