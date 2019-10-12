package com.baeldung.architecture.hexagonal.dao;

import com.baeldung.architecture.hexagonal.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao{

    private List<Employee> employees = new ArrayList<Employee>();

    public void createEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployeeList() {
        return employees;
    }


}
