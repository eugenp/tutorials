package com.baeldung.hexagonal;

import com.baeldung.hexagonal.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class SecondaryAdapter implements OutboundPort {

    private List<Employee> employees = new ArrayList<Employee>();

    public void createEmployee(Employee employee) {
      employees.add(employee);
    }

    public List<Employee> getEmployeeList() {
        return employees;
    }
}
