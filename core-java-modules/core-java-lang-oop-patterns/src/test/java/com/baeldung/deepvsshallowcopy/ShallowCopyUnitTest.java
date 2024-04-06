package com.baeldung.deepvsshallowcopy;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShallowCopyUnitTest {

    @Test
    public void whenShallowCopyIsPerformed_thenCopiedEmployeeHasSameAttributesButDifferentIdentity() {

        Employee originalEmployee = new Employee("John Doe", new Company("Baeldung"));

        Employee copyEmployee = new Employee(originalEmployee.getName(), originalEmployee.getCompany());

        assertSame(originalEmployee.getCompany(), copyEmployee.getCompany());
        assertSame(originalEmployee.getName(), copyEmployee.getName());
        assertNotSame(originalEmployee, copyEmployee);
    }

    @Test
    public void whenCompanyNameIsChangedInCopy_thenCompanyNameIsChangedInOriginal() {

        Employee originalEmployee = new Employee("John Doe", new Company("Baeldung"));
        Employee copyEmployee = new Employee(originalEmployee.getName(), originalEmployee.getCompany());

        copyEmployee.getCompany().setName("ABC Corp.");

        assertEquals(originalEmployee.getCompany().getName(), copyEmployee.getCompany().getName());
    }

    @Test
    @Ignore
    public void whenEmployeeIsShallowCloned_thenCopiedEmployeeHasSameAttributesButDifferentIdentity() {

        Employee originalEmployee = new Employee("John Doe", new Company("Baeldung"));

        Employee copyEmployee = originalEmployee.clone();

        assertSame(originalEmployee.getCompany(), copyEmployee.getCompany());
        assertSame(originalEmployee.getName(), copyEmployee.getName());
        assertNotSame(originalEmployee, copyEmployee);
    }

}
