package com.baeldung.lambda.todo.service;

import com.baeldung.lambda.todo.api.ToDoApi;
import com.baeldung.lambda.todo.api.ToDoItem;
import com.baeldung.lambda.todo.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class ToDoReaderService {
    // Log4j logger
    private static final Logger LOGGER = LogManager.getLogger(ToDoReaderService.class);

    private ToDoApi toDoApi;

    @Inject
    public ToDoReaderService(Config configuration, ToDoApi toDoApi) {
        LOGGER.info("ToDo Endpoint on: {}", configuration.getToDoEndpoint());

        this.toDoApi = toDoApi;
    }

    public Optional<ToDoItem> getOldestToDo() {
        return toDoApi.getAllTodos().stream()
          .filter(item -> !item.isCompleted())
          .findFirst();
    }
}
