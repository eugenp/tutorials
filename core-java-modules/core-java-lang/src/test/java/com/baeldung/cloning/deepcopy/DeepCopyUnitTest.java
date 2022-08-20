package com.baeldung.cloning.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenShallowCopying_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {

        Department department = new Department(1, "IT");
        Employee originalEmployee = new Employee(1, "Dinesh Varyani", department);
        Employee clonedEmployee = (Employee) originalEmployee.clone();
        assertThat(originalEmployee)
            .isNotSameAs(clonedEmployee);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() throws CloneNotSupportedException {
        Department department = new Department(1, "IT");
        Employee originalEmployee = new Employee(1, "Dinesh Varyani", department);
        Employee clonedEmployee = (Employee) originalEmployee.clone();
        clonedEmployee.getDepartment().setName("HR");
        assertThat(originalEmployee.getDepartment().getName())
            .isNotEqualTo(clonedEmployee.getDepartment().getName());
    }
}
