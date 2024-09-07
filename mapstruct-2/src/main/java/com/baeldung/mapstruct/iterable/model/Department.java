package com.baeldung.mapstruct.iterable.model;

import java.util.List;

public class Department {

    private List<Employee> employees;
    private Employee manager;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}