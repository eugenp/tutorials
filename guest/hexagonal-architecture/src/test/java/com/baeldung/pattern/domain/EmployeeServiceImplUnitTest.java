package com.baeldung.pattern.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.pattern.port.EmployeeRepository;
import com.baeldung.pattern.port.EmployeeService;

class EmployeeServiceImplUnitTest {

    EmployeeService employeeService;

    EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void givenEmployeeID_whenGetEmployee_thenBonusCalculated_andEmployeeReturned() {

        Mockito.when(employeeRepository.findById("emp001"))
            .thenReturn(Optional.of(Employee.builder()
                .employeeId("emp001")
                .department("IT")
                .salary(100)
                .build()));
        Employee employee = employeeService.getEmployee("emp001");
        assertThat(employee.getDepartment()).isEqualTo("IT");
        assertThat(employee.getSalary()).isEqualTo(100);
        assertThat(employee.getBonus()).isEqualTo(new BigDecimal("10.0"));
    }

}