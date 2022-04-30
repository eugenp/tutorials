package org.baeldung.copy;

import org.baeldung.copy.shallow.Department;
import org.baeldung.copy.shallow.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowCopyTest {

    @Test
    public void copyElementsShallowCopyTests() {
        Department dept = new Department("D03", "Finance");
        Employee emp = new Employee("EM012", "John White", dept);
        Employee copy = new Employee(emp.getEmployeeId(), emp.getName(), emp.getDepartment());

        assertEquals(emp.getEmployeeId(), copy.getEmployeeId());
        assertEquals(emp.getName(), copy.getName());
        assertEquals(emp.getDepartment(), copy.getDepartment());

        copy.setName("Anne Smith");
        assertNotEquals(emp.getName(), copy.getName());

        dept.setDepartmentName("IT");
        assertEquals(emp.getDepartment(), copy.getDepartment());
    }

    @Test
    public void cloneableInterfaceShallowCopyTest() throws CloneNotSupportedException {
        Department dept = new Department("D03", "Finance");
        Employee emp = new Employee("EM012", "John White", dept);
        Employee copy = (Employee) emp.clone();

        assertEquals(emp.getEmployeeId(), copy.getEmployeeId());
        assertEquals(emp.getName(), copy.getName());
        assertEquals(emp.getDepartment(), copy.getDepartment());

        copy.setName("Anne Smith");
        assertNotEquals(emp.getName(), copy.getName());

        dept.setDepartmentName("IT");
        assertEquals(emp.getDepartment(), copy.getDepartment());
    }
}
