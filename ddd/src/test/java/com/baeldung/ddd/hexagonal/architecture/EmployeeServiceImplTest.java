package com.baeldung.ddd.hexagonal.architecture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;
import com.baeldung.ddd.hexagonal.architecture.domain.EmployeeServiceImpl;
import com.baeldung.ddd.hexagonal.architecture.output.port.EmployeeDatabaseService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeDatabaseService empDatabaseSvc;
    
    @InjectMocks
    private EmployeeServiceImpl empServiceImpl;
    
    @Test
    public void givenEmployeeData_whenSaveEmployee_thenSaveEmployeeDetailsAndReturnID() {
        Employee e1 = new Employee ("James", "Warner");
        Mockito.doReturn("1234-4567").when(empDatabaseSvc).saveEmployee(e1);
        Assertions.assertEquals("1234-4567", empServiceImpl.saveEmployeeDetails(e1));
    }
    
    @Test
    public void whenGetEmployee_thenReturnEmployeeDetails() {
        Employee e1 = new Employee("James", "Warner");
        e1.setId("1234-4567");
        Mockito.doReturn(e1).when(empDatabaseSvc).getEmployee(Mockito.any());
        Employee e = empServiceImpl.getEmployeeDetailsById("1234-4567");
        Assertions.assertEquals("1234-4567", e1.getId());
    }
    
    @Test
    public void whenDeleteEmployee_thenDeleteEmployeeDetails() {
        Mockito.doNothing().when(empDatabaseSvc).deleteEmployee(Mockito.any());
        empServiceImpl.deleteEmployeeDetailsById("1234-4567");
        Mockito.verify(empDatabaseSvc).deleteEmployee(Mockito.any());
    }
    
    @Test
    public void givenEmployeeDetails_whenUpdateEmployee_thenUpdateTheDetails() {
        Mockito.doNothing().when(empDatabaseSvc).updateEmployee(Mockito.any());
        Employee e1 = new Employee("James", "Warner");
        e1.setId("1234-4567");
        empServiceImpl.updateEmployeeDetailsById(e1);
        Mockito.verify(empDatabaseSvc).updateEmployee(Mockito.any());
    }
}
