package com.baeldung.hexagonal.crud.domain.ports.exception;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -3009964266867354724L;

    public UserNotFoundException(String message){
        super(message);
    }
}