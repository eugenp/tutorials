package com.baeldung.hexagonal.adapter;

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

    public static class Param {
        String todoName;

        public String getTodoName() {
            return todoName;
        }

        public void setTodoName(String todoName) {
            this.todoName = todoName;
        }
    }

    public void createNewToDoItem(Param param) {
        createToDoPort.createTask(param.getTodoName());
    }
}
