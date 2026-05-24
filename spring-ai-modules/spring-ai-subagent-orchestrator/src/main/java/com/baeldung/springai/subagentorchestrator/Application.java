package com.baeldung.springai.subagentorchestrator;

import com.baeldung.springai.subagentorchestrator.config.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner demo(OrchestratorService orchestratorService) {
        return args -> {
            String response = orchestratorService.ask(
                """
                Perform the following tasks:
                - Review the code quality of a current Java Spring Boot application
                - Generate concise technical documentation like user guide
                """
            );
            logger.info("{}", response);
        };
    }
}
