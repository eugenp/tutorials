package com.java.test;

import java.io.*;
import org.junit.Test;

import junit.framework.Assert;

public class DeserializationExample {

    @Test
    public void deserializationTest() throws Exception {

        // Serialized data is saved in /src/com/java/test/employee.ser
        // Deserialized Employee
        Employee empDeserialized = (Employee) FileUtility.deserializeTheObjectBack("src/com/java/test/employee.ser");
        Assert.assertEquals(empDeserialized.id, "123");
        Assert.assertEquals(empDeserialized.name, "Derek");

    }
}
