package com.baeldung.patterns.hexagonal.service;

import com.baeldung.patterns.hexagonal.dao.EmployeeDaoImpl;
import com.baeldung.patterns.hexagonal.domain.Employee;
import com.baeldung.patterns.hexagonal.service.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

@SpringBootTest
class EmployeeServiceImplTest {

    private static Map<Integer, Employee> map = new HashMap<Integer, Employee>() {
        {
            put(1, new Employee(1, "Dhruv", "Kochhar"));
            put(2, new Employee(2, "Vivek", "Chauhan"));
        }
    };

    @MockBean
    private EmployeeDaoImpl employeeDaoImpl;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Test
    void getAllEmployees() {
        Mockito.when(employeeDaoImpl.getAllEmployees())
            .thenReturn(new ArrayList<>(map.values()));
        assertEquals(employeeServiceImpl.getAllEmployees()
            .size(), 2);
    }

    @Test
    void addEmployee() {
        employeeServiceImpl.addEmployee(new Employee(3, "Ishu", "Arora"));
        Mockito.verify(employeeDaoImpl, atLeastOnce())
            .addEmployee(any());
    }

    @Test
    void getEmployeesById() {
        Mockito.when(employeeDaoImpl.getEmployeesById(anyInt()))
            .thenReturn(new Employee(2, "Vivek", "Chauhan"));
        assertEquals(employeeServiceImpl.getEmployeesById(2)
            .getEmployeeId(), 2);
    }
}