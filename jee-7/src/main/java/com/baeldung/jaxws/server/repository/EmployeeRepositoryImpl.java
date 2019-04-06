package com.baeldung.jaxws.server.repository;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.jaxws.server.bottomup.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.server.bottomup.exception.EmployeeNotFound;
import com.baeldung.jaxws.server.bottomup.model.Employee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private List<Employee> employeeList;

    public EmployeeRepositoryImpl() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Jane"));
        employeeList.add(new Employee(2, "Jack"));
        employeeList.add(new Employee(3, "George"));
    }

    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee getEmployee(int id) throws EmployeeNotFound {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        throw new EmployeeNotFound();
    }

    public Employee updateEmployee(int id, String name) throws EmployeeNotFound {
        for (Employee employee1 : employeeList) {
            if (employee1.getId() == id) {
                employee1.setId(id);
                employee1.setFirstName(name);
                return employee1;
            }
        }
        throw new EmployeeNotFound();
    }

    public boolean deleteEmployee(int id) throws EmployeeNotFound {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                employeeList.remove(emp);
                return true;
            }
        }
        throw new EmployeeNotFound();
    }

    public Employee addEmployee(int id, String name) throws EmployeeAlreadyExists {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                throw new EmployeeAlreadyExists();
            }
        }
        Employee employee = new Employee(id, name);
        employeeList.add(employee);
        return employee;
    }

    public int count() {
        return employeeList.size();
    }
}
