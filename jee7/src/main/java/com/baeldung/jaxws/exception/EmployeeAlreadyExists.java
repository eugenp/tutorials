package com.baeldung.jaxws.exception;

import java.io.Serializable;

import javax.xml.ws.WebFault;

@WebFault
public class EmployeeAlreadyExists extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmployeeAlreadyExists() {
        super("This employee already exists");
    }

    public EmployeeAlreadyExists(String str) {
        super(str);
    }
}
