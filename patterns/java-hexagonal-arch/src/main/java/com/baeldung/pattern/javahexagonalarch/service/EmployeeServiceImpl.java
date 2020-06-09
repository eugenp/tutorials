package com.baeldung.pattern.javahexagonalarch.service;

import com.baeldung.pattern.javahexagonalarch.dao.EmployeeDao;
import com.baeldung.pattern.javahexagonalarch.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service public class EmployeeServiceImpl implements EmployeeService {

        @Autowired private EmployeeDao employeeDao;

        @Override public ArrayList<Employee> getAllEmployees() {
                return employeeDao.getAllEmployees();
        }

        @Override public void addEmployee(Employee e) {
                employeeDao.addEmployee(e);
        }

        @Override public Employee getEmployeesById(int id) {
                return employeeDao.getEmployeesById(id);
        }

}
