package com.baeldung.copyconstructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.Test;

public class EmployeeUnitTest {
    @Test
    public void givenCopyConstructor_whenDeepCopy_thenDistinct() {
        Date d1 = new Date(123);
        Employee e1 = new Employee(1, "Baeldung", d1);
        Employee e2 = new Employee(e1);
        assertEquals(d1, e1.getStartDate());
        assertEquals(d1, e2.getStartDate());

        d1.setTime(456);
        assertEquals(d1, e1.getStartDate());
        assertNotEquals(d1, e2.getStartDate());
    }

    @Test
    public void givenCopyMethod_whenCopy_thenDistinct() {
        Date d1 = new Date(123);
        Employee e1 = new Employee(1, "Baeldung", d1);
        Employee e2 = e1.copy();
        assertEquals(d1, e1.getStartDate());
        assertEquals(d1, e2.getStartDate());

        d1.setTime(456);
        assertEquals(d1, e1.getStartDate());
        assertNotEquals(d1, e2.getStartDate());
    }
}
