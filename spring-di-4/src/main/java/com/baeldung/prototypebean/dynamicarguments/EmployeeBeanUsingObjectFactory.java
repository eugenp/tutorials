package com.baeldung.prototypebean.dynamicarguments;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeBeanUsingObjectFactory {

    @Autowired
    private ObjectFactory<Employee> employeeObjectFactory;

    public Employee getEmployee() {
        return employeeObjectFactory.getObject();
    }
}