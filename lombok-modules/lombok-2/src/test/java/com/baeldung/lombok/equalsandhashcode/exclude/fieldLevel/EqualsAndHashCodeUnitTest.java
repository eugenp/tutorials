package com.baeldung.lombok.equalsandhashcode.exclude.fieldLevel;

import com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel.Employee;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualsAndHashCodeUnitTest {

    @Test
    public void whenUsingEqualsAndHashCodeWithExcludingFields_thenTwoObjectsAreEqualAndHaveSameHashCodeEvenIfExcludedFieldsAreNotSame() {
        Employee employee = new Employee("Nimi", 15, 22);
        Employee employeeTwo = new Employee("Nimi", 17, 21);

        assertEquals(employee, employeeTwo);
        assertEquals(employee.hashCode(), employeeTwo.hashCode());
    }
}
