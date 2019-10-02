package com.baeldung.architecture.commands.task;

import com.baeldung.architecture.domain.task.Task;

public interface GetAllTasks {
    public Iterable<Task> getAll();
}
