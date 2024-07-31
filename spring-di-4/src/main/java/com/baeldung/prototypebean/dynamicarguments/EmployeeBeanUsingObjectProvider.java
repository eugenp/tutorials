package com.baeldung.prototypebean.dynamicarguments;

import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeBeanUsingObjectProvider {

    @Autowired
    private org.springframework.beans.factory.ObjectProvider<Employee> objectProvider;

    public Employee getEmployee(String name) {
        Employee employee = objectProvider.getObject(name);
        return employee;
    }
}
