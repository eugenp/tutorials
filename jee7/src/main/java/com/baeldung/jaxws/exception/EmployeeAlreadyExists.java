package com.baeldung.jaxws.exception;

public class EmployeeAlreadyExists extends RuntimeException {

    public EmployeeAlreadyExists() {
        super("This employee already exist");
    }
}
