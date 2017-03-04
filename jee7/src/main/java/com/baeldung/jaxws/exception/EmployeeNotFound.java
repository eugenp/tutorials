package com.baeldung.jaxws.exception;

public class EmployeeNotFound extends RuntimeException {

    public EmployeeNotFound() {
        super("The specified employee does not exist");
    }

}
