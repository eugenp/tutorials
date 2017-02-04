package com.baeldung.javaSerialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;
import org.junit.*;
import com.baeldung.javaSerialization.Customer;
import com.baeldung.javaSerialization.FileUtility;

public class CustomerSerializationDeserializationUnitTest {

    @Test
    public void givenCustomer_whenSerializedOrDeserialized_thenOk() throws Exception {
        Customer customer = new Customer();
        customer.id = "A-23825";
        customer.name = "Adrian Peterson";
        customer.address = "Regent Street , 2nd cross";
        customer.designation = "Software Engineer";

        FileUtility.serializeGivenObject("com/baeldung/javaSerialization/customer.ser", customer);

        customer = (Customer) FileUtility.deserializeTheObjectBack("com/baeldung/javaSerialization/customer.ser");

        Assert.assertEquals(customer.id, "A-23825");
        Assert.assertEquals(customer.name, "Adrian Peterson");
        Assert.assertNull(customer.address, null);
        Assert.assertEquals(customer.designation, "Software Engineer");

    }
}
