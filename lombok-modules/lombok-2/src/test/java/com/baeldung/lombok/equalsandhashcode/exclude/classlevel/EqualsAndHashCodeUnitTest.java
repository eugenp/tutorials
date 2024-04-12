package com.baeldung.lombok.equalsandhashcode.exclude.classlevel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeOnClassLevelAndExcludingFields_thenTwoObjectsAreEqualAndHaveSameHashCodeEvenIfExcludedFieldsAreNotSame() {
        Employee employee = new Employee("Nimi", 15, 22);
        Employee employeeTwo = new Employee("Nimi", 17, 21);

        assertEquals(employee, employeeTwo);
        assertEquals(employee.hashCode(), employeeTwo.hashCode());
    }
}
