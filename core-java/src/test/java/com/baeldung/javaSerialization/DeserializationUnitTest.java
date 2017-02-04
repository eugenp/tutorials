package com.baeldung.javaSerialization;
import com.baeldung.javaSerialization.Employee;
import com.baeldung.javaSerialization.FileUtility;

import java.io.*;
import org.junit.Test;

import junit.framework.Assert;

public class DeserializationExample {

    @Test
    public void givenSerializedEmployee_whenDeserialized_thenOk() throws Exception {

        // Serialized data is saved in com/baeldung/javaSerialization/employee.ser
        // Deserialized Employee
        Employee empDeserialized = (Employee) FileUtility.deserializeTheObjectBack("com/baeldung/javaSerialization/employee.ser");
        Assert.assertEquals(empDeserialized.id, "123");
        Assert.assertEquals(empDeserialized.name, "Derek");

    }
}
