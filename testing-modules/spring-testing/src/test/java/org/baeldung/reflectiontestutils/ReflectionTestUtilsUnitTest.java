package org.baeldung.reflectiontestutils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.baeldung.reflectiontestutils.repository.Employee;
import org.baeldung.reflectiontestutils.repository.EmployeeService;
import org.baeldung.reflectiontestutils.repository.HRService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.when;

public class ReflectionTestUtilsUnitTest {

    @Test
    public void whenNonPublicField_thenReflectionTestUtilsSetField() {
        Employee employee = new Employee();
        ReflectionTestUtils.setField(employee, "id", 1);
        assertTrue(employee.getId().equals(1));

    }

    @Test
    public void whenNonPublicMethod_thenReflectionTestUtilsInvokeMethod() {
        Employee employee = new Employee();
        ReflectionTestUtils.setField(employee, "id", 1);
        employee.setName("Smith, John");
        assertTrue(ReflectionTestUtils.invokeMethod(employee, "employeeToString").equals("id: 1; name: Smith, John"));
    }

    @Test
    public void whenInjectingMockOfDependency_thenReflectionTestUtilsSetField() {
        Employee employee = new Employee();
        ReflectionTestUtils.setField(employee, "id", 1);
        employee.setName("Smith, John");

        HRService hrService = mock(HRService.class);
        when(hrService.getEmployeeStatus(employee.getId())).thenReturn("Active");
        EmployeeService employeeService = new EmployeeService();

        // Inject mock into the private field
        ReflectionTestUtils.setField(employeeService, "hrService", hrService);
        assertEquals("Employee " + employee.getId() + " status: Active", employeeService.findEmployeeStatus(employee.getId()));
    }
}
