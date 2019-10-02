package com.baeldung.architecture.commands.task;

import com.baeldung.architecture.domain.task.Task;

public interface CreateTask {
    public void create(Task newTask);
}
