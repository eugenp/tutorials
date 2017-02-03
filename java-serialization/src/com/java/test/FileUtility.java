package com.java.test;
import org.junit.Assert;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FileUtility {
    public static void serializeGivenObject(String filePath , Object object ) {
        FileOutputStream fileOut =
          new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    public static Object deserializeTheObjectBack(String filePath) {
        FileInputStream fileIn =
          new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object deserializedObject = in.readObject();
        in.close();
        fileIn.close();
        return deserializedObject;
    }

       
    @Test 
    public void employeeSerializationDeserializationTest() {
        Emp emp=new Emp();
        emp.setId(123);
        emp.setName("Derek");
        FileUtility.serializeGivenObject("src/com/java/test/employee.ser",emp);

        Emp empDeserialized = (Emp) FileUtility.deserializeTheObjectBack("src/com/java/test/employee.ser");
        Assert.assertEquals(emp.id, 123);
        Assert.assertEquals(emp.name, "Derek");
        
    }
}



