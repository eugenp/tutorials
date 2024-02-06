package com.baeldung.shallowndeepcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CustomerDeepCopyUnitTest {

    @Test
    public void whenDeepCopyObject_thenIndependentReferenceObjectCopyCreated() {
        AddressDeepCopy addressDeepCopy = new AddressDeepCopy("Trivendram");
        CustomerDeepCopy customerOriginal = new CustomerDeepCopy(addressDeepCopy, "John Doe");

        CustomerDeepCopy customerCopy = null;

        try {
            customerCopy = (CustomerDeepCopy) customerOriginal.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertEquals(customerOriginal.addressDeepCopy.city, customerCopy.addressDeepCopy.city);

        customerCopy.addressDeepCopy.city = "Kozikode";

        assertNotEquals(customerOriginal.addressDeepCopy.city, customerCopy.addressDeepCopy.city);
    }
}
