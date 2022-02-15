package com.baeldung.hexagonaljava.application.services;

import java.util.Calendar;

import com.baeldung.hexagonaljava.application.entities.Employee;
import com.baeldung.hexagonaljava.ports.drivenports.EmployeePersistence;
import com.baeldung.hexagonaljava.ports.driverports.EmployeeService;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeePersistence employeePersistence;

    public EmployeeServiceImpl(EmployeePersistence employeePersistence) {
        this.employeePersistence = employeePersistence;
    }

    @Override
    public String addEmployee(String name, int yearOfBirth) {
        int currentYear = Calendar.getInstance()
            .get(Calendar.YEAR);
        int employeeAge = currentYear - yearOfBirth;
        Employee employee = new Employee();
        employee.setAge(employeeAge);
        employee.setName(name);
        return employeePersistence.saveEmployee(employee);
    }
}
