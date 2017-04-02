package com.baeldung.jaxws.exception;

import javax.xml.ws.WebFault;
import java.io.Serializable;

@WebFault
public class EmployeeNotFound extends Exception implements Serializable {

    public EmployeeNotFound() {
        super("The specified employee does not exist");
    }

    public EmployeeNotFound(String str) {
        super(str);
    }

}
