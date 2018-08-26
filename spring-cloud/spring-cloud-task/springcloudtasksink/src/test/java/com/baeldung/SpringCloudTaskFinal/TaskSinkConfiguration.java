package com.baeldung.SpringCloudTaskFinal;

import static org.mockito.Mockito.mock;

import org.springframework.cloud.deployer.spi.task.TaskLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TaskSinkConfiguration {

    @Bean
    public TaskLauncher taskLauncher() {
        return  mock(TaskLauncher.class);
  }
    
}