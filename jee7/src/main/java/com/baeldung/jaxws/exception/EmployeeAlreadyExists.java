package com.baeldung.jaxws.exception;

import javax.xml.ws.WebFault;
import java.io.Serializable;

@WebFault
public class EmployeeAlreadyExists extends Exception implements Serializable {

    public EmployeeAlreadyExists() {
        super("This employee already exist");
    }

    public EmployeeAlreadyExists(String str) {
        super(str);
    }
}
