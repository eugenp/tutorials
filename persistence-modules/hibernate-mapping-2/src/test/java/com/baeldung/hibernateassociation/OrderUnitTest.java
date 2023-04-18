package com.baeldung.hibernateassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderUnitTest {

    private Order order;
    private Item item1;
    private Item item2;

    @BeforeEach
    public void setup() {
        List<Item> items = new ArrayList<>();
        item1 = new Item(1);
        item2 = new Item(2);
        items.add(item1);
        items.add(item2);
        order = new Order(100, items);
        item1.setOrder(order);
        item2.setOrder(order);
    }

    @Test
    public void getOrder_returnsCorrectOrder() {
        assertEquals(order, item1.getOrder());
        assertEquals(order, item2.getOrder());
    }

    @Test
    public void getItems_returnsCorrectItems() {
        List<Item> items = order.getItems();
        assertEquals(2, items.size());
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }

    @Test
    public void setOrder_updatesOrderCorrectly() {
        Order newOrder = new Order(200, new ArrayList<>());
        item1.setOrder(newOrder);
        assertNull(item2.getOrder());
        assertEquals(newOrder, item1.getOrder());
    }

}
