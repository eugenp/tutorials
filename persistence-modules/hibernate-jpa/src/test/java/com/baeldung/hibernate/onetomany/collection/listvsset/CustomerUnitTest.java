package com.baeldung.hibernate.onetomany.collection;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerUnitTest {
    @Test
    public void givenCustomers_whenAddedOrders_thenCheckBehavior() {
        OrderEntity order1 = new OrderEntity();
        order1.setItem("item1");

        OrderEntity order2 = new OrderEntity();
        order2.setItem("item2");

        OrderEntity order3 = new OrderEntity();
        order3.setItem("item1"); // duplicate item

        List<OrderEntity> orderList = Arrays.asList(order1, order2, order3);
        Set<OrderEntity> orderSet = new HashSet<>(orderList);

        CustomerEntity customerList = new CustomerEntity();
        customerList.setOrderList(orderList);

        CustomerEntity customerSet = new CustomerEntity();
        customerSet.setOrderSet(orderSet);

        assertAll("Customer with List vs Set",
                () -> assertEquals(3, customerList.getOrderList().size(), "List allows duplicates and thus, size is 3"),
                () -> assertEquals(2, customerSet.getOrderSet().size(), "Set doesn't allow duplicates and thus, size is 2"),
                () -> assertTrue(customerList.getOrderList().contains(order3), "List maintains duplicates"),
                () -> assertFalse(customerSet.getOrderSet().contains(order3), "Set removes duplicates"),
                () -> assertNotEquals(customerList.getOrderList(), customerSet.getOrderSet(), "List and Set are not equal due to different rules on duplicates and order")
        );
    }
}