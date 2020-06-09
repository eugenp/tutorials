package com.baeldung.pattern.javahexagonalarch.service;

import com.baeldung.pattern.javahexagonalarch.dao.EmployeeDaoImpl;
import com.baeldung.pattern.javahexagonalarch.domain.Employee;
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

@SpringBootTest class EmployeeServiceImplTest {

        private static Map<Integer, Employee> map = new HashMap<Integer, Employee>() {{
                put(1, new Employee(1, "John", "Kay"));
                put(2, new Employee(2, "Jen", "Day"));
        }};

        @MockBean private EmployeeDaoImpl employeeDaoImpl;

        @Autowired private EmployeeServiceImpl employeeServiceImpl;

        @Test void getAllEmployees() {
                Mockito.when(employeeDaoImpl.getAllEmployees()).thenReturn(new ArrayList<>(map.values()));
                assertEquals(employeeServiceImpl.getAllEmployees().size(), 2);
        }

        @Test void addEmployee() {
                employeeServiceImpl.addEmployee(new Employee(3, "John2", "Kay"));
                Mockito.verify(employeeDaoImpl, atLeastOnce()).addEmployee(any());
        }

        @Test void getEmployeesById() {
                Mockito.when(employeeDaoImpl.getEmployeesById(anyInt())).thenReturn(new Employee(2, "Jen", "Day"));
                assertEquals(employeeServiceImpl.getEmployeesById(2).getEmployeeId(), 2);
        }
}