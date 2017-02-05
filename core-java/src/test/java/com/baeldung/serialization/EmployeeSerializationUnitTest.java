package com.baeldung.serialization;

import java.io.*;
import org.junit.Test;
import org.junit.*;
import com.baeldung.serialization.Employee;
import com.baeldung.serialization.FileUtility;

public class EmployeeSerializationUnitTest {

    @Test
    public void givenEmployee_whenSerialized_thenOk() throws Exception {
        Employee employee = new Employee();
        employee.setId("123");
        employee.setName("Derek");
        FileUtility.serializeGivenObject("com/baeldung/serialization/employee.ser", employee);
    }
	
	@Test
    public void givenSerializedEmployee_whenDeserialized_thenOk() throws Exception {

        // Serialized data is saved in com/baeldung/serialization/employee.ser
        // Deserialized Employee
        Employee empDeserialized = (Employee) FileUtility.deserializeTheObjectBack("com/baeldung/serialization/employee.ser");
        Assert.assertEquals(empDeserialized.id, "123");
        Assert.assertEquals(empDeserialized.name, "Derek");

    }
}
