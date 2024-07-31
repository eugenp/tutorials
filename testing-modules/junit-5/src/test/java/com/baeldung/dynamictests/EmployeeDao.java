package com.baeldung.dynamictests;

public class EmployeeDao {

    public Employee save(long id) {
        return new Employee(id);
    }

    public Employee save(long id, String firstName) {
        return new Employee(id, firstName);
    }

    public Employee update(Employee employee) {
        return employee;
    }
}
