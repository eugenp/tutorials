package com.baeldung.lombok.equalsandhashcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeOnClassLevel_thenTwoObjectsAreEqualAndHaveSameHashCodeIfAllFieldsAreSame() {
        Employee employee = new Employee("Nimi", 15, 22);
        Employee employeeTwo = new Employee("Nimi", 15, 22);
        Employee employeeThree = new Employee("Mannat", 15, 22);
        assertEquals(employee, employeeTwo);
        assertNotEquals(employee, employeeThree);
        assertEquals(employee.hashCode(), employeeTwo.hashCode());
        assertNotEquals(employee.hashCode(), employeeThree.hashCode());
    }
}
