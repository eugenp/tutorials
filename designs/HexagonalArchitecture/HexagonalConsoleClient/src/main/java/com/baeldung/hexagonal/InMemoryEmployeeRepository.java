package com.baeldung.hexagonal;

import java.util.Hashtable;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeRepository;

public class InMemoryEmployeeRepository implements EmployeeRepository {

    private static Hashtable<Integer, Employee> listOfEmployees = new Hashtable<Integer, Employee>();
    private static Integer employeeKey = 1;

    public Employee saveEmployee(Employee employee) {
        listOfEmployees.put(employeeKey, employee);
        employee.setEmployeeId(employeeKey);
        employeeKey++;
        return employee;
    }

}
