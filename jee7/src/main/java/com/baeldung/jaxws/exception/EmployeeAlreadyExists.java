package com.baeldung.jaxws.exception;

import javax.xml.ws.WebFault;

@WebFault
public class EmployeeAlreadyExists extends Exception {

    public EmployeeAlreadyExists() {
        super("This employee already exists");
    }

    public EmployeeAlreadyExists(String str) {
        super(str);
    }
}
