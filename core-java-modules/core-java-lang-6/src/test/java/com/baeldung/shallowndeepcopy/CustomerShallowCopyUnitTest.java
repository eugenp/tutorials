/**
 * 
 */
package com.baeldung.shallowndeepcopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerShallowCopyUnitTest {

    @Test
    public void whenShallowCopyObject_thenIndependentReferenceObjectCopyNotCreated() {
        AddressShallowCopy addressShallowCopy = new AddressShallowCopy("Trivendram");
        CustomerShallowCopy customerOriginal = new CustomerShallowCopy(addressShallowCopy, "John Doe");

        CustomerShallowCopy customerCopy = null;

        try {
            customerCopy = (CustomerShallowCopy) customerOriginal.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertEquals(customerOriginal.addressShallowCopy.city, customerCopy.addressShallowCopy.city);

        customerCopy.addressShallowCopy.city = "Kozikode";

        assertEquals(customerOriginal.addressShallowCopy.city, customerCopy.addressShallowCopy.city);
    }
}
