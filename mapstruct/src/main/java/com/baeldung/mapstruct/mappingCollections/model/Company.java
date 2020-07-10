package com.baeldung.mapstruct.mappingCollections.model;

import java.util.List;

public class Company {

    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
