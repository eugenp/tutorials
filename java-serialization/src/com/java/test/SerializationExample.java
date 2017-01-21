package com.java.test;

import java.io.*;
import org.junit.Test;
import org.junit.*;

public class SerializationExample {
	public static void main(String[] args) {
		Employee emp = null;
		try {
			emp = new Employee();
			emp.id = "A-23825";
			emp.name = "Adrian Peterson";
			emp.address = "Regent Street , 2nd cross";
			FileOutputStream fileOut = new FileOutputStream("src/com/java/test/employee.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(emp);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in src/com/java/test/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	@Test
    public void serializationTest() throws Exception {
        Employee emp = null;
        emp = new Employee();
        emp.id = "A-23825";
        emp.name = "Adrian Peterson";
        emp.address = "Regent Street , 2nd cross";
        try {
	        FileOutputStream fileOut = 
	          new FileOutputStream("src/com/java/test/employee.ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(emp);   // Serialized data is saved in src/com/java/test/employee.ser
	        out.close();
	        fileOut.close();
        } catch(Exception e) {
        	throw new Exception("Error while serializing "+e.getMessage());
        	
        } 
   }
}
