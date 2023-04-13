package com.baeldung.lambda.todo.api;

import feign.RequestLine;

import java.util.List;

public interface ToDoApi {
    @RequestLine("GET /todos")
    List<ToDoItem> getAllTodos();
}
