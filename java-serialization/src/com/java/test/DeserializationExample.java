package com.java.test;

import java.io.*;
import org.junit.Test;

import junit.framework.Assert;

public class DeserializationExample {

	
	@Test
    public void deserializationTest() {
        Employee emp = null;
        try {
	        FileInputStream fileIn = 
	          new FileInputStream("src/com/java/test/employee.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        emp = (Employee) in.readObject();
	        in.close();
	        fileIn.close();
        } catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
		}
        // Serialized data is saved in /src/com/java/test/employee.ser
        // Deserialized Employee
        Assert.assertEquals(emp.id, "A-23825");
        Assert.assertEquals(emp.name, "Adrian Peterson");
        Assert.assertEquals(emp.address, "Regent Street , 2nd cross");
        
    }
}
