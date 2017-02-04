package com.baeldung.javaSerialization.externalizable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.baeldung.javaSerialization.externalizable.Employee;
import com.baeldung.javaSerialization.externalizable.ExternalizationUnitTest;

import org.junit.Test;
import org.junit.*;

/**
 * Externalization Example Program.
 * 
 */
public class ExternalizationUnitTest {

    private String filePath = "com/baeldung/javaSerialization/externalizable/employee.ser";

    public Employee serialize() throws IOException {
        Employee emp = new Employee();

        emp.setId(73737);
        emp.setName("Roger");
        emp.setAddress("Robinson Road , 2nd Cross");

        // Serializing the object's state
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream outputStream = new ObjectOutputStream(fos);
        outputStream.writeObject(emp); // Serialization done
        outputStream.close();
        return emp;
    }

    public Employee deserialize() throws ClassNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream inputStream = new ObjectInputStream(fis);
        Employee emp = (Employee) inputStream.readObject();
        inputStream.close();
        return emp;
    }

    @Test
    public void givenEmployee_whenSerializedOrDeserialized_thenOk() throws Exception {
        ExternalizationUnitTest externalizationExample = new ExternalizationUnitTest();
        Employee empAfterSerialization = externalizationExample.serialize();
        Assert.assertEquals(empAfterSerialization.id, 73737);
        Assert.assertEquals(empAfterSerialization.name, "Roger");
        Assert.assertEquals(empAfterSerialization.address, "Robinson Road , 2nd Cross");

        Employee empAfterDeserialization = externalizationExample.deserialize();
        Assert.assertEquals(empAfterSerialization.id, 73737);
        Assert.assertEquals(empAfterSerialization.name, "Roger");
        Assert.assertEquals(empAfterSerialization.address, "Robinson Road , 2nd Cross");
    }
}
