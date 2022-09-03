package com.baeldung.shallowdeepcopy;

import org.junit.Test;

import main.java.com.baeldung.shallowdeepcoy.Order;
import main.java.com.baeldung.shallowdeepcoy.Voucher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ShallowCopyDeepCopyUnitTest {

    @Test
    public void whenShallow_thenInnerObjectIsShared(){
        Voucher voucher = new Voucher("Learn2022", 0.25);
        Order order = new Order("Baeldung Java Courses", 1000, voucher);
        Order shallowCopyOrder = new Order(order, Order.COPY_TYPE.SHALLOW);
        shallowCopyOrder.getVoucher().setCode("Java2022");
        assertEquals(order.getVoucher(), shallowCopyOrder.getVoucher());
        assertEquals(order.getVoucher().getCode(), "Java2022");
    }

    @Test
    public void whenShallow_thenImmutablesAreNotShared(){
        Voucher voucher = new Voucher("Learn2022", 0.25);
        Order order = new Order("Baeldung Java Courses", 1000, voucher);
        Order shallowCopyOrder = new Order(order, Order.COPY_TYPE.SHALLOW);
        shallowCopyOrder.setItem("Baeldung System Design Courses");
        assertFalse(order.getItem().equals(shallowCopyOrder.getItem()));
    }

    @Test
    public void whenDeep_thenInnerObjectIsNotShared(){
        Voucher voucher = new Voucher("Learn2022", 0.25);
        Order order = new Order("Baeldung Java Courses", 1000, voucher);
        Order deepCopyOrder = new Order(order, Order.COPY_TYPE.DEEP);
        assertFalse(order.getVoucher().equals(deepCopyOrder.getVoucher()));
        deepCopyOrder.getVoucher().setCode("Java2022");
        assertEquals(order.getVoucher().getCode(), "Learn2022");
        assertEquals(deepCopyOrder.getVoucher().getCode(), "Java2022");
    }
}
