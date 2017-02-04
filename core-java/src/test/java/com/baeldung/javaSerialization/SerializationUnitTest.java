package com.baeldung.javaSerialization;

import java.io.*;
import org.junit.Test;
import org.junit.*;
import com.baeldung.javaSerialization.Employee;
import com.baeldung.javaSerialization.FileUtility;

public class SerializationUnitTest {

    @Test
    public void givenEmployee_whenSerialized_thenOk() throws Exception {
        Employee employee = new Employee();
        employee.setId("123");
        employee.setName("Derek");
        FileUtility.serializeGivenObject("com/baeldung/javaSerialization/employee.ser", employee);
    }
}
