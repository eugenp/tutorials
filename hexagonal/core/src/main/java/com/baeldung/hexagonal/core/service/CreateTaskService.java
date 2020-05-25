package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.domain.Todo;
import com.baeldung.hexagonal.core.port.provided.ICreateToDoPort;
import com.baeldung.hexagonal.core.port.required.IPersistToDoPort;

public class CreateTaskService implements ICreateToDoPort {

    private IPersistToDoPort persistToDoPort;

    public CreateTaskService(IPersistToDoPort persistToDoPort) {
        this.persistToDoPort = persistToDoPort;
    }

    public void createTask(String name) {
        Todo item = new Todo();
        item.setName(name);
        item.setChecked(false);
        persistToDoPort.persistTodo(item);
    }
}
