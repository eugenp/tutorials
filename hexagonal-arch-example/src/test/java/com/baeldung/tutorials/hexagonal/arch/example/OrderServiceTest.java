package com.baeldung.tutorials.hexagonal.arch.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceTest.class)
public class OrderServiceTest {
 
    @InjectMocks
    private OrderService service;
 
    @Mock
    private OrderDao dao;
 
    @Test
    public void whenUnitCountGreaterThenHundred_thenOrderIsMajor() {
 
        Order majorOrder = new Order();
 
        majorOrder.setUnitCount(1001);
        doReturn(majorOrder).when(dao).get(1);
        assertEquals(true, service.isMajor(1));
    }
 
    @Test
    public void whenUnitCountLessThenHundred_thenOrderIsNotMajor() {
 
        Order notMajorOrder = new Order();
 
        notMajorOrder.setUnitCount(1000);
        doReturn(notMajorOrder).when(dao).get(1);
        assertEquals(false, service.isMajor(1));
    }
}
