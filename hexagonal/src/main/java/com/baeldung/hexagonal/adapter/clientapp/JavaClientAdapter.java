package com.baeldung.hexagonal.adapter.clientapp;

import com.baeldung.hexagonal.adapter.persist.InMemoryToDoAdapter;
import com.baeldung.hexagonal.core.port.provided.ICreateToDoPort;
import com.baeldung.hexagonal.core.port.required.IPersistToDoPort;
import com.baeldung.hexagonal.core.service.CreateTaskService;

public class JavaClientAdapter {

    private ICreateToDoPort createToDoPort;
    private IPersistToDoPort persistToDoPort;

    public JavaClientAdapter() {
        IPersistToDoPort persistToDoPort = new InMemoryToDoAdapter();
        this.createToDoPort = new CreateTaskService(persistToDoPort);
    }

    public void createNewToDoItem(String todo) {
        createToDoPort.createTask(todo);
    }
}
