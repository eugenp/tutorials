package com.baeldung.prevent.commandline.application.runner.execution;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@ConditionalOnProperty(prefix = "command.line.runner", 
                       value = "enabled", 
                       havingValue = "true", 
                       matchIfMissing = true)
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private TaskService taskService;

    public CommandLineTaskExecutor(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        taskService.execute("command line runner task");
    }
}
