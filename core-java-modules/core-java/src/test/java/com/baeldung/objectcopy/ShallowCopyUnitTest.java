package com.baeldung.objectcopy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowCopyUnitTest {

    @Test
    public void testShallowCopy() {
        Employee employee1 = new Employee("1");

        ShallowCopy shallowCopy = new ShallowCopy(employee1);
        employee1.setEmployeeId("2");
        assertEquals(shallowCopy.employee, employee1);

    }

}