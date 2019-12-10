package com.baeldung.exceptions.custom;

public class InvalidDataException extends Exception {
    
    public InvalidDataException(Exception e) {
        super(e);
    }
}
