package com.baeldung.prevent.commandline.application.runner.execution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.prevent.commandline.application.runner.execution.ApplicationRunnerTaskExecutor;
import com.baeldung.prevent.commandline.application.runner.execution.CommandLineTaskExecutor;
import com.baeldung.prevent.commandline.application.runner.execution.TaskService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class RunApplicationWithTestProfileIntegrationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void whenContextLoads_thenTheCommandLineAndApplicationRunnerAreNotLoaded() {
        assertNotNull(context.getBean(TaskService.class));
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(CommandLineTaskExecutor.class),
                "CommandLineRunner should not be loaded during this integration test");
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(ApplicationRunnerTaskExecutor.class),
                "ApplicationRunner should not be loaded during this integration test");
    }
}
