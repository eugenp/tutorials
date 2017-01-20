package com.java.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;
import org.junit.*;

public class CustomerSerializationDeserializationExample {

	public static void main(String[] args) {
		Customer customer = null;
		try {
			customer = new Customer();
			customer.id = "A-23825";
			customer.name = "Adrian Peterson";
			customer.address = "Regent Street , 2nd cross";
			customer.designation = "Software Engineer";
			FileOutputStream fileOut = new FileOutputStream("src/com/java/test/customer.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(customer);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in src/com/java/test/customer.ser");

			FileInputStream fileIn = new FileInputStream("src/com/java/test/customer.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			try {
				customer = (Customer) in.readObject();
			} catch (ClassNotFoundException c) {
				System.out.println("Customer class not found");
			}
			in.close();
			fileIn.close();
			System.out.printf("\nDeserialized Customer object " + customer);

		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	@Test
    public void customerSerializationDeserializationTest() throws Exception{
        Customer customer = null;
        customer = new Customer();
        customer.id = "A-23825";
        customer.name = "Adrian Peterson";
        customer.address = "Regent Street , 2nd cross";
        customer.designation = "Software Engineer";
        try {
	        FileOutputStream fileOut = new FileOutputStream("src/com/java/test/customer.ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(customer);
	        out.close();
	        fileOut.close();
	        
	
	        FileInputStream fileIn = new FileInputStream("src/com/java/test/customer.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        customer = (Customer) in.readObject();
	        
	        Assert.assertEquals(customer.id, "A-23825");
	        Assert.assertEquals(customer.name, "Adrian Peterson");
	        Assert.assertNull(customer.address, null);
	        Assert.assertEquals(customer.designation, "Software Engineer");
	        in.close();
	        fileIn.close();
        } catch(Exception e) {
        	throw new Exception("Error occurred in Customer Serialization/Deserialization "+e.getMessage());
        }
        
    }

}
