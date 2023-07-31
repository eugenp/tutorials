package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {
    @Test
    public void givenCopyCustomerWithClone_whenAddressChangedForCopy_thenAddressNotChangedForOriginal() throws CloneNotSupportedException {
        CustomerInfo CustomerInfo = new CustomerInfo("111 Main Road", 1234.0);
        Customer originalCustomer = new Customer("Jane", "Doe", CustomerInfo);
        Customer deepCopy = (Customer) originalCustomer.clone();

        deepCopy.getCustomerInfo()
            .setAddress("204 Main Road");

        assertNotEquals(deepCopy.getCustomerInfo()
            .getAddress(), originalCustomer.getCustomerInfo()
            .getAddress());
    }

    @Test
    public void givenCopyCustomerWithCopyConstructor_whenAddressChangedForCopy__thenAddressNotChangedForOriginal() throws CloneNotSupportedException {
        CustomerInfo CustomerInfo = new CustomerInfo("111 Main Road", 1234.0);
        Customer originalCustomer = new Customer("Jane", "Doe", CustomerInfo);
        Customer deepCopy = new Customer(originalCustomer);

        deepCopy.getCustomerInfo()
            .setAddress("204 Main Road");

        assertNotEquals(deepCopy.getCustomerInfo()
            .getAddress(), originalCustomer.getCustomerInfo()
            .getAddress());
    }
}
