package com.baeldung.hexagonal.crud.domain.ports.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }
}
