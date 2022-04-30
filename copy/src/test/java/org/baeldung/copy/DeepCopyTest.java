package org.baeldung.copy;

import org.baeldung.copy.deep.Department;
import org.baeldung.copy.deep.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepCopyTest {

    @Test
    public void cloneableInterfaceDeepCopyTest() throws CloneNotSupportedException {
        Department dept = new Department("D03", "Finance");
        Employee emp = new Employee("EM012", "John White", dept);
        Employee copy = emp.clone();

        copy.setName("Anne Smith");
        assertNotEquals(emp.getName(), copy.getName());

        dept.setDepartmentName("IT");
        assertNotEquals(emp.getDepartment(), copy.getDepartment());
    }

    @Test
    public void copyConstructorDeepCopyTest() throws CloneNotSupportedException {
        Department dept = new Department("D03", "Finance");
        Employee emp = new Employee("EM012", "John White", dept);
        Employee copy = new Employee(emp);

        copy.setName("Anne Smith");
        assertNotEquals(emp.getName(), copy.getName());

        dept.setDepartmentName("IT");
        assertNotEquals(emp.getDepartment(), copy.getDepartment());
    }
}
