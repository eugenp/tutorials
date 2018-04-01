package com.midgetontoes.todolist;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User %s not found.", username));
    }
}
