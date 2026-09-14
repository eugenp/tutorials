package com.baeldung.springai.todowritetool;

import com.baeldung.springai.todowritetool.config.TodoAgentService;
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
    CommandLineRunner demo(TodoAgentService todoAgentService) {
        return args -> {
            String response = todoAgentService.ask(
                """
                Track these steps as a todo list: set up the project, write the tool, add tests
                """
            );
            logger.info("{}", response);
        };
    }
}
