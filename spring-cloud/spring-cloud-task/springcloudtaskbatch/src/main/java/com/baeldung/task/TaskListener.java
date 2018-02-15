package com.baeldung.task;

import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;

public class TaskListener implements TaskExecutionListener {

    @Override
    public void onTaskEnd(TaskExecution arg0) {
        // TODO Auto-generated method stub
        System.out.println("End of task");
    }

    @Override
    public void onTaskFailed(TaskExecution arg0,
        Throwable arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTaskStartup(TaskExecution arg0) {
        // TODO Auto-generated method stub
        System.out.println("Task startup ");
    }

}
