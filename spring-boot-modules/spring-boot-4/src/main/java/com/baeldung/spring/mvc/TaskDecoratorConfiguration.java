package com.baeldung.spring.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskDecorator;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TaskDecoratorConfiguration {

    @Bean
    @Order(2)
    TaskDecorator loggingTaskConfigurator() {
        return runnable -> () -> {
            log.info("Running Task: {}", runnable);
            try {
                runnable.run();
            } finally {
                log.info("Finished Task: {}", runnable);
            }
        };
    }

    @Bean
    @Order(1)
    TaskDecorator measuringTaskConfigurator() {
        return runnable -> () -> {
            final var ts1 = System.currentTimeMillis();
            try {
                runnable.run();
            } finally {
                final var ts2 = System.currentTimeMillis();
                log.info("Finished within {}ms (Task: {})", ts2 - ts1, runnable);
            }
        };
    }

}
