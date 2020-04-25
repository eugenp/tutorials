package com.baeldung.architecture.framework.cli;

import com.baeldung.architecture.application.task.AddNewDailyTask;
import com.baeldung.architecture.domain.task.Task;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    AddNewDailyTask addNewDailyTask;

    public StartupRunner(AddNewDailyTask addNewDailyTask) {
        this.addNewDailyTask = addNewDailyTask;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Task task = new Task();
        task.setDescription("Startup Task");
        addNewDailyTask.create(task);
    }
}