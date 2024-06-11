package com.baeldung.prevent.commandline.application.runner.execution;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@ConditionalOnProperty(
  prefix = "application.runner", 
  value = "enabled", 
  havingValue = "true", 
  matchIfMissing = true)
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

    private final TaskService taskService;

    public ApplicationRunnerTaskExecutor(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(ApplicationArguments args) {
        taskService.execute("application runner task");
    }

}
