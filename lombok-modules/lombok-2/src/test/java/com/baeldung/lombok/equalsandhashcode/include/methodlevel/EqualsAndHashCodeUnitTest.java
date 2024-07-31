package com.baeldung.lombok.equalsandhashcode.include.methodlevel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeOnMethodLevel_thenTwoObjectsAreEqualAndHaveSameHashCodeIfMethodsReturnSameValue() {
        Employee employee = new Employee("Nimi", 15, 22);
        Employee employeeTwo = new Employee("Nimi", 15, 22);
        Employee employeeThree = new Employee("Nimi", 15, 23);

        assertEquals(employee, employeeTwo);
        assertNotEquals(employee, employeeThree);

        assertEquals(employee.hashCode(), employeeTwo.hashCode());
        assertNotEquals(employee.hashCode(), employeeThree.hashCode());
    }
}
