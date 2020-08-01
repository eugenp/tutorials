package com.baeldung.patterns.hexagonal.dao;

import org.springframework.stereotype.Repository;

import com.baeldung.patterns.hexagonal.domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static Map<Integer, Employee> employeeList = new HashMap<Integer, Employee>();

    static {
        Employee employee = new Employee(1, "Dhruv", "Kochhar");
        employeeList.put(1, employee);
    }
    
    public ArrayList<Employee> getAllEmployees() {
        return new ArrayList<Employee>(employeeList.values());
    }

    public void addEmployee(Employee e) {
        employeeList.put(e.getEmployeeId(), e);
    }

    public Employee getEmployeesById(int id) {
        return employeeList.get(id);
    }

}
