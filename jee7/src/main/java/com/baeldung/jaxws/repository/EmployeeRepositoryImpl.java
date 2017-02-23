package com.baeldung.jaxws.repository;

import com.baeldung.jaxws.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.exception.EmployeeNotFound;
import com.baeldung.jaxws.model.Employee;

import java.util.ArrayList;
import java.util.List;


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

    public Employee getEmployee(int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        throw new EmployeeNotFound();
    }

    public Employee updateEmployee(Employee update, int id) {
        for (Employee employee1 : employeeList) {
            if (employee1.getId() == id) {
                employee1.setId(update.getId());
                employee1.setFirstName(update.getFirstName());
                return employee1;
            }
        }
        throw new EmployeeNotFound();
    }

    public boolean deleteEmployee(int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                employeeList.remove(emp);
                return true;
            }
        }
        throw new EmployeeNotFound();
    }

    public Employee addEmployee(Employee employee) {
        for (Employee emp : employeeList) {
            if (emp.getId() == employee.getId()) {
                throw new EmployeeAlreadyExists();
            }
        }
        employeeList.add(employee);
        return employee;
    }

    public int count() {
        return employeeList.size();
    }
}
