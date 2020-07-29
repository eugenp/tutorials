package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Employee;
import com.baeldung.pattern.hexagonal.persistence.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceImplUnitTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService testService;
    private Employee testModel;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);

        testService = new EmployeeServiceImpl(employeeRepository);
        testModel = new Employee();
        testModel.setEmpId("2000");
        testModel.setEmpName("Test user 1");
        testModel.setEmpJobTitle("Software engineer");
    }

    @Test
    void addEmployee() {
        when(employeeRepository.add(any(Employee.class))).thenReturn(testModel);

        Employee testResponse = testService.addEmployee(testModel);
        assertEquals(testModel, testResponse);
    }

    @Test
    void getEmployee() {
        when(employeeRepository.findById("2000")).thenReturn(Optional.of(testModel));

        Employee testResponse = testService.getEmployee("2000");
        assertEquals(testModel, testResponse);
    }
}