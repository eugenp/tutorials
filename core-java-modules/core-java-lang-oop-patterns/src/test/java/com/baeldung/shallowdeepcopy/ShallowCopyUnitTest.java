package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {
    @Test
    public void givenCopyEmployeeWithClone_whenSalaryChangedForCopy_thenSalaryChangedForOriginal() throws CloneNotSupportedException {
        EmployeeInfo employeeInfo = new EmployeeInfo("Software Developer", 2234);
        Employee originalEmployee = new Employee("Jane", "Doe", employeeInfo);
        Employee shallowCopy = (Employee) originalEmployee.clone();

        shallowCopy.getEmployeeInfo()
            .setSalary(12313);

        assertEquals(shallowCopy.getEmployeeInfo()
            .getSalary(), originalEmployee.getEmployeeInfo()
            .getSalary());
    }

    @Test
    public void givenCopyEmployeeWithCopyConstructor_whenSalaryChangedForCopy_thenSalaryChangedForOriginal() throws CloneNotSupportedException {
        EmployeeInfo employeeInfo = new EmployeeInfo("Software Developer", 2234);
        Employee originalEmployee = new Employee("Jane", "Doe", employeeInfo);
        Employee shallowCopy = new Employee(originalEmployee);

        shallowCopy.getEmployeeInfo()
            .setSalary(12313);

        assertEquals(shallowCopy.getEmployeeInfo()
            .getSalary(), originalEmployee.getEmployeeInfo()
            .getSalary());
    }
}
