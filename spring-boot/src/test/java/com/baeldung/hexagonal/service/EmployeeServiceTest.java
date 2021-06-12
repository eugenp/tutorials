package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.port.EmployeeRepositoryPort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepositoryPort employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void create_whenEmployeeInfoCorrect_thenCreateEmployeeInDB(){
        when(employeeRepository.create(anyString(), anyString(), anyLong())).thenReturn(true);
        boolean isSaved = employeeService.create("Zordan", "Mananger", 10000);
        assertTrue(isSaved);
    }

    @Test
    public void view_whenEmployeeExist_theReturnEmployeeFromDB(){
        Employee employee = new Employee(1, "Zordan", "Mananger", 10000);
        when(employeeRepository.getEmployee(anyInt())).thenReturn(employee);
        Employee employeeDetail = employeeService.view(1);
        assertEquals(employee, employeeDetail);
    }



}
