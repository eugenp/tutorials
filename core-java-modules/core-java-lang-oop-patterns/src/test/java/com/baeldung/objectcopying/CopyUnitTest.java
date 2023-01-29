package com.baeldung.objectcopying;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyUnitTest {

    Employee employee = new Employee(1, "Tom", "Smith", new Department(1, "dept1"));

    @Test
    void copiedFieldWouldChangeAfterOriginalFieldChangedWhenApplyShallowCopy() {
        Employee copiedEmployee = new Employee(
                employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getDepartment());

        assertNotSame(copiedEmployee, employee);
        assertEquals(copiedEmployee, employee);

        employee.getDepartment().setName("dept2");
        assertEquals("dept2", copiedEmployee.getDepartment().getName());
    }

    @Test
    void nestedObjectShouldNotChangeWhenOriginalFieldChangedWhenApplyDeepCopy_constructor() {
        Employee copiedEmployee = new Employee(
                employee.getId(), employee.getFirstName(), employee.getLastName(), new Department(employee.getDepartment()));

        assertEquals(copiedEmployee, employee);

        employee.getDepartment().setName("dept2");
        assertEquals("dept1", copiedEmployee.getDepartment().getName());
    }

    @Test
    void nestedObjectShouldNotChangeWhenOriginalFieldChangedWhenApplyDeepCopy_clone() {
        Employee copiedEmployee = employee.deepClone();

        assertEquals(employee, copiedEmployee);

        employee.getDepartment().setName("dept2");
        assertEquals("dept1", copiedEmployee.getDepartment().getName());
    }

    @Test
    void nestedObjectShouldNotChangeWhenOriginalFieldChangedWhenApplyDeepCopy_json() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Employee copiedEmployee = om.readValue(om.writeValueAsString(employee), Employee.class);

        assertEquals(employee, copiedEmployee);

        employee.getDepartment().setName("dept2");
        assertEquals("dept1", copiedEmployee.getDepartment().getName());
    }

}