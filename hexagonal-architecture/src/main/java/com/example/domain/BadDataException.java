package com.example.domain;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}
