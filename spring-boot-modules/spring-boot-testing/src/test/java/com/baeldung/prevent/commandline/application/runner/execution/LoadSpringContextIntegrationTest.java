package com.baeldung.prevent.commandline.application.runner.execution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.prevent.commandline.application.runner.execution.ApplicationCommandLineRunnerApp;
import com.baeldung.prevent.commandline.application.runner.execution.TaskService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ApplicationCommandLineRunnerApp.class }, 
  initializers = ConfigFileApplicationContextInitializer.class)
public class LoadSpringContextIntegrationTest {
    @SpyBean
    TaskService taskService;

    @SpyBean
    CommandLineRunner commandLineRunner;

    @SpyBean
    ApplicationRunner applicationRunner;

    @Test
    void whenContextLoads_thenRunnersDoNotRun() throws Exception {
        assertNotNull(taskService);
        assertNotNull(commandLineRunner);
        assertNotNull(applicationRunner);

        verify(taskService, times(0)).execute(any());
        verify(commandLineRunner, times(0)).run(any());
        verify(applicationRunner, times(0)).run(any());
    }
}
