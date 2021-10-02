package com.baeldung.ddd.hexagonal.architecture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;
import com.baeldung.ddd.hexagonal.architecture.domain.EmployeeServiceImpl;
import com.baeldung.ddd.hexagonal.architecture.output.port.EmployeeDatabaseService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplUnitTest {

    private EmployeeDatabaseService empDatabaseSvc;
    private EmployeeServiceImpl empServiceImpl;
    
    @BeforeEach
    void init() {
        empDatabaseSvc = mock(EmployeeDatabaseService.class);
        empServiceImpl = new EmployeeServiceImpl(empDatabaseSvc);
    }
    
    @Test
    public void givenEmployeeData_whenSaveEmployee_thenSaveEmployeeDetailsAndReturnID() {
        Employee e1 = new Employee ("James", "Warner");
        when(empDatabaseSvc.saveEmployee(e1)).thenReturn("1234-4567");
        Assertions.assertEquals("1234-4567", empServiceImpl.saveEmployeeDetails(e1));
    }
    
    @Test
    public void whenGetEmployee_thenReturnEmployeeDetails() {
        Employee e1 = new Employee("James", "Warner");
        e1.setId("1234-4567");
        when(empDatabaseSvc.getEmployee(Mockito.any())).thenReturn(e1);
        Employee e = empServiceImpl.getEmployeeDetailsById("1234-4567");
        Assertions.assertEquals("1234-4567", e1.getId());
    }
    
}
