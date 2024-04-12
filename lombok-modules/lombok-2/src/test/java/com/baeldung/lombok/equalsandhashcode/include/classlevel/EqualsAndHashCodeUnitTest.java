package com.baeldung.lombok.equalsandhashcode.include.classlevel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeOnClassLevelAndIncludeFields_thenTwoObjectsAreEqualAndHaveSameHashCodeIfIncludedFieldsAreSame() {
        Employee employee = new Employee("Nimi", 15, 22);
        Employee employeeTwo = new Employee("Nimi", 15, 22);
        Employee employeeThree = new Employee("Nimi", 15, 23);

        assertEquals(employee, employeeTwo);
        assertEquals(employee, employeeThree);

        assertEquals(employee.hashCode(), employeeTwo.hashCode());
        assertEquals(employee.hashCode(), employeeThree.hashCode());
    }
}
