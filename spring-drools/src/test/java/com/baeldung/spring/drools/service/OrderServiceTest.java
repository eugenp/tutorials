package com.baeldung.spring.drools.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.spring.drools.model.Order;
import com.baeldung.spring.drools.model.Result;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class OrderServiceTest {

    ApplicationContext context = null;
    OrderService orderService = null;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(OrderServiceConfiguration.class);
        orderService = (OrderService) context.getBean(OrderService.class);
    }

    @Test
    public void testCalculateBillingDayCharge() {
        Order order = new Order();
        order.setNightFlag(false);
        order.setServiceName("Cab");
        order.setServiceType("Transport");
        order.setTimeInMinutes(100L);
        Result result = new Result();
        Long totalCharge = orderService.calculateBilling(order, result);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(200), totalCharge);
    }

    @Test
    public void testCalculateBillingNightFlatCharge() {
        Order order = new Order();
        order.setNightFlag(true);
        order.setServiceName("Cab");
        order.setServiceType("Transport");
        order.setTimeInMinutes(9L);
        Result result = new Result();
        Long totalCharge = orderService.calculateBilling(order, result);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(100), totalCharge);
    }

    @Test
    public void testCalculateBillingNightVarCharge() {
        Order order = new Order();
        order.setNightFlag(true);
        order.setServiceName("Cab");
        order.setServiceType("Transport");
        order.setTimeInMinutes(90L);
        Result result = new Result();
        Long totalCharge = orderService.calculateBilling(order, result);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(280), totalCharge);
    }
}
