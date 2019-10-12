package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.dao.EmployeeDao;
import com.baeldung.architecture.hexagonal.model.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }

    public void createEmployee(Employee employee) {
        employeeDao.createEmployee(employee);

    }

    public List<Employee> getEmployeeList() {
        return employeeDao.getEmployeeList();
    }
}
