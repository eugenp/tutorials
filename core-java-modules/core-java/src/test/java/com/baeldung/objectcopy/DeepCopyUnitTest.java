package com.baeldung.objectcopy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DeepCopyUnitTest {

    @Test
    public void testDeepCopy() {
        Employee employee1 = new Employee("1");

        DeepCopy deepCopy = new DeepCopy(employee1);
        employee1.setEmployeeId("2");
        assertNotEquals(deepCopy.employee, employee1);

    }

}