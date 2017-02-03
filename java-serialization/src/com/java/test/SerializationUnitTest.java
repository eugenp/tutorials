package com.java.test;

import java.io.*;
import org.junit.Test;
import org.junit.*;

public class SerializationUnitTest {

    @Test
    public void serializationTest() throws Exception {
        Employee employee = new Employee();
        employee.setId("123");
        employee.setName("Derek");
        FileUtility.serializeGivenObject("src/com/java/test/employee.ser", employee);
    }
}
