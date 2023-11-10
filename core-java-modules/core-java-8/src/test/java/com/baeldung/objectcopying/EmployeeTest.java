package com.baeldung.objectcopying;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EmployeeTest {
    @Test
    public void testShallowCopy() {
        Employee employee1 = new Employee("Void", new Department("HR", "Human Resources"));
        Employee employee2 = employee1.deepCopy();

        assertEquals(employee1.getName(), employee2.getName());
        assertEquals(employee1.getDepartment(), employee2.getDepartment());

        employee1.getDepartment().setCode("HuRe");

        assertEquals(employee1.getDepartment(), employee2.getDepartment());
    }

    @Test
    public void testDeepCopy() {
        Employee employee1 = new Employee("Void", new Department("HR", "Human Resources"));
        Employee employee2 = employee1.deepCopy();

        assertEquals(employee1.getName(), employee2.getName());
        assertEquals(employee1.getDepartment(), employee2.getDepartment());

        employee1.getDepartment().setCode("HuRe");

        assertNotEquals(employee1.getDepartment(), employee2.getDepartment());
    }
}