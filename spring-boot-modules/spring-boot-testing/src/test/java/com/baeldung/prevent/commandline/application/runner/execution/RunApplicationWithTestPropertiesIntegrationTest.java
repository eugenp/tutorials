package com.baeldung.prevent.commandline.application.runner.execution;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.baeldung.prevent.commandline.application.runner.execution.ApplicationRunnerTaskExecutor;
import com.baeldung.prevent.commandline.application.runner.execution.CommandLineTaskExecutor;
import com.baeldung.prevent.commandline.application.runner.execution.TaskService;

@SpringBootTest(properties = { 
  "command.line.runner.enabled=false", 
  "application.runner.enabled=false" })
class RunApplicationWithTestPropertiesIntegrationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void whenContextLoads_thenRunnersAreNotLoaded() {
        assertNotNull(context.getBean(TaskService.class));
        assertThrows(NoSuchBeanDefinitionException.class, 
          () -> context.getBean(CommandLineTaskExecutor.class), 
          "CommandLineRunner should not be loaded during this integration test");
        assertThrows(NoSuchBeanDefinitionException.class, 
          () -> context.getBean(ApplicationRunnerTaskExecutor.class), 
          "ApplicationRunner should not be loaded during this integration test");
    }
}
