package com.midgetontoes.todolist;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Requested resource doesn't exist.");
    }
}
