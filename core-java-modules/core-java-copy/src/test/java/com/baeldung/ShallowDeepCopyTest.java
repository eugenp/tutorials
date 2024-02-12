package com.baeldung;

import com.baeldung.model.Account;
import com.baeldung.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShallowDeepCopyTest {

    @Test
    public void when_modifying_original_customer_then_shallow_copy_should_change() {
        Account originalAccount = new Account("112233_test", 1000.1);
        Customer originalCustomer = new Customer("Baeldung_test", 30, originalAccount);

        Customer shallowCopyCustomer = new Customer(originalCustomer.getName(), originalCustomer.getAge(), originalCustomer.getAccount());

        //Modifying Original
        originalAccount.setBalance(732.5);

        // Assert that both objects now share the same modified data
        assertEquals(originalCustomer.getAccount(), shallowCopyCustomer.getAccount());
    }

    @Test
    public void when_modifying_original_customer_then_deep_copy_should_not_change() {
        Account originalAccount = new Account("112233_test", 1000.1);
        Customer originalCustomer = new Customer("Baeldung_test", 30, originalAccount);

        Customer deepCopyCustomer = new Customer(originalCustomer);

        //Modifying Original
        originalAccount.setBalance(800.0);

        // Assert that deep-copied object has an independent copy of data
        assertNotEquals(originalCustomer.getAccount(), deepCopyCustomer.getAccount());
    }

@Test
public void when_modifying_original_customer_then_deep_copy_should_not_change_when_using_cloneable_interface() {
    Account originalAccount = new Account("112233_test", 1000.1);
    Customer originalCustomer = new Customer("Baeldung_test", 30, originalAccount);

    Customer deepCopyCustomer = (Customer) originalCustomer.clone();
    //Modifying Original
    originalAccount.setBalance(800.0);

    // Assert that deep-copied object has an independent copy of data
    assertNotEquals(originalCustomer.getAccount(), deepCopyCustomer.getAccount());
}

}