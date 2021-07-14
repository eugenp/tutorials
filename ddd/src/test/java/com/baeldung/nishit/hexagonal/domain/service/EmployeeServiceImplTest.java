package com.baeldung.nishit.hexagonal.domain.service;


import com.baeldung.nishit.hexagonal.adapter.secondary.EmployeeRepositoryImpl;
import com.baeldung.nishit.hexagonal.application.port.outbound.EmployeeRepository;
import com.baeldung.nishit.hexagonal.domain.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeService;


    @BeforeEach
    public void init() {

        employeeService = new EmployeeServiceImpl();
        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
        ReflectionTestUtils.setField(employeeService, "employeeRepository", employeeRepository);
    }

    @Test
    public void shouldCreateEmployee_thenSaveit() {
        Employee employee = employeeService.addEmployee("Nishit", 30);
        Employee e2 = employeeService.getEmployee(employee.getId());
        assertEquals(e2.getName(), "Nishit");
        assertEquals(e2.getAge(), 30);
    }

    @Test
    public void shouldDeleteEmployee() {
        Employee employee = employeeService.addEmployee("Nishit", 30);
        employeeService.deleteEmployee(employee.getId());
        Employee e2 = employeeService.getEmployee(employee.getId());
        assertNull(e2);
    }
}