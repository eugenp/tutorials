package com.java.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;
import org.junit.*;

public class CustomerSerializationDeserializationUnitTest {

    @Test
    public void customerSerializationDeserializationTest() throws Exception {
        Customer customer = new Customer();
        customer.id = "A-23825";
        customer.name = "Adrian Peterson";
        customer.address = "Regent Street , 2nd cross";
        customer.designation = "Software Engineer";

        FileUtility.serializeGivenObject("src/com/java/test/customer.ser", customer);

        customer = (Customer) FileUtility.deserializeTheObjectBack("src/com/java/test/customer.ser");

        Assert.assertEquals(customer.id, "A-23825");
        Assert.assertEquals(customer.name, "Adrian Peterson");
        Assert.assertNull(customer.address, null);
        Assert.assertEquals(customer.designation, "Software Engineer");

    }
}
