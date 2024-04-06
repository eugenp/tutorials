package com.baeldung.deepvsshallowcopy;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeepCopyUnitTest {

    @Test
    public void whenDeepCopyIsPerformed_thenCopiedEmployeeAndCompanyAreDifferentInstances(){
        Employee originalEmployee = new Employee("John Doe", new Company("Baeldung"));

        Employee copyEmployee = originalEmployee.clone();

        assertNotSame(originalEmployee, copyEmployee);
        assertNotSame(originalEmployee.getCompany(), copyEmployee.getCompany());
    }

    @Test
    public void whenCompanyNameIsChangedInDeepCopy_thenCompanyNameIsNotChangedInOriginal() {
        Employee originalEmployee = new Employee("John Doe", new Company("Baeldung"));
        Employee copyEmployee = originalEmployee.clone();

        copyEmployee.getCompany().setName("ABC Corp.");

        assertNotEquals(originalEmployee.getCompany().getName(), copyEmployee.getCompany().getName());
    }

}
