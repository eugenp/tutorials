package com.baeldung.jaxws.exception;

import javax.xml.ws.WebFault;

@WebFault
public class EmployeeNotFound extends Exception {

    public EmployeeNotFound() {
        super("The specified employee does not exist");
    }

    public EmployeeNotFound(String str) {
        super(str);
    }

}
