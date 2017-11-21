package com.baeldung.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Department {

    private String name = "Technology";

    /* Dependency/collaborator */
    private Employee employee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    /* Mandatory dependency since a
     * department will have an employee.
     */
    @Autowired
    public Department(Employee employee) {
        this.employee = employee;
    }

}
