package com.baeldung.hexagonal.core;

public class TodoItemAlreadyExistsException extends TodoException {

    private String todoDescription;

    public TodoItemAlreadyExistsException(String todoDescription) {
        this.todoDescription = todoDescription;
    }

}
