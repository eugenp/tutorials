package com.baeldung.problemdetails.model;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String s) {
        super(s);
    }
}
