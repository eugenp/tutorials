package com.baeldung.innerinterfaces;

import com.baeldung.innerinterfaces.CommaSeparatedCustomers;
import com.baeldung.innerinterfaces.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class InnerInterfaceUnitTest {
    @Test
    public void whenCustomerListJoined_thenReturnsJoinedNames() {
        Customer.List customerList = new CommaSeparatedCustomers();
        customerList.Add(new Customer("customer1"));
        customerList.Add(new Customer("customer2"));
        assertEquals("customer1,customer2", customerList.getCustomerNames());
    }
}
