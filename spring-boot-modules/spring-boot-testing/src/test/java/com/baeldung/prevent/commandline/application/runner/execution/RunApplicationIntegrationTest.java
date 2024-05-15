package com.baeldung.prevent.commandline.application.runner.execution;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class RunApplicationIntegrationTest {
    @SpyBean
    ApplicationRunnerTaskExecutor applicationRunnerTaskExecutor;
    @SpyBean
    CommandLineTaskExecutor commandLineTaskExecutor;

    @Test
    void whenContextLoads_thenRunnersRun() throws Exception {
        verify(applicationRunnerTaskExecutor, times(1)).run(any());
        verify(commandLineTaskExecutor, times(1)).run(any(String[].class));
    }
}
