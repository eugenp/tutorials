package com.baeldung.architecture.hexagonal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.architecture.hexagonal.adapters.primary.EmployeeRestAdapter;
import com.baeldung.architecture.hexagonal.domain.Employee;
import com.baeldung.architecture.hexagonal.port.inbound.HandleUserInteraction;

@SpringBootTest
public class EmployeeRestTest {

    @Autowired
    private HandleUserInteraction employeeService;

    @Test
    public void whenCreatingEmployee_ThenSuccessful() {
        employeeService.addNewEmployee(new Employee(1, "Test User"));
    }

    @Test
    public void whenCreatingEmployee_ThenSuccessfulRetrieval() {
        int employeeId = 2;
        employeeService.addNewEmployee(new Employee(employeeId, "Test User"));
        Employee employee = employeeService.getEmployeeById(employeeId);
        assertNotNull(employee);
        assertEquals(employeeId, employee.getId());
    }

    @Test
    public void whenSearchingNonExistentEmployee_ThenReturnNull() {
        Random rand = new Random();
        int random_id = rand.nextInt(1000);
        Employee nonExistentEmployee = employeeService.getEmployeeById(random_id);
        assertNull(nonExistentEmployee);
    }

}
