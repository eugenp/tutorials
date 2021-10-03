package com.baeldung.ddd.hexagonal.architecture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.baeldung.ddd.hexagonal.architecture.domain.Employee;
import com.baeldung.ddd.hexagonal.architecture.domain.EmployeeServiceImpl;
import com.baeldung.ddd.hexagonal.architecture.output.port.EmployeeDatabaseService;

public class EmployeeServiceImplUnitTest {

    
    @Test
    public void givenEmployeeData_whenSaveEmployee_thenSaveEmployeeDetailsAndReturnID() {
        EmployeeDatabaseService empDatabaseSvc = mock(EmployeeDatabaseService.class);
        EmployeeServiceImpl empServiceImpl = new EmployeeServiceImpl(empDatabaseSvc);
        Employee e1 = new Employee ("James", "Warner");
        when(empDatabaseSvc.saveEmployee(e1)).thenReturn("1234-4567");
        Assertions.assertEquals("1234-4567", empServiceImpl.saveEmployeeDetails(e1));
    }
    
    @Test
    public void whenGetEmployee_thenReturnEmployeeDetails() {
        Employee e1 = new Employee("James", "Warner");
        e1.setId("1234-4567");
        EmployeeDatabaseService empDatabaseSvc = mock(EmployeeDatabaseService.class);
        EmployeeServiceImpl empServiceImpl = new EmployeeServiceImpl(empDatabaseSvc);
        when(empDatabaseSvc.getEmployee(Mockito.any())).thenReturn(e1);
        Employee e = empServiceImpl.getEmployeeDetailsById("1234-4567");
        Assertions.assertEquals("1234-4567", e1.getId());
    }
    
}
