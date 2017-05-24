package com.baeldung.didemo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.didemo.model.Order;
import com.baeldung.didemo.service.CustomerServiceConstructorDI;
import com.baeldung.didemo.service.CustomerServiceFieldDI;
import com.baeldung.didemo.service.CustomerServiceSetterDI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataInjectionDemoApplicationTests {

    @Autowired
    CustomerServiceConstructorDI constructorDI;

    @Autowired
    CustomerServiceFieldDI fieldDI;

    @Autowired
    CustomerServiceSetterDI setterDI;
    
	@Test
	public void testConstructorDI() {
	    List<Order> orders = constructorDI.getCustomerOrders(1l);
	    Assert.assertNotNull(orders);
	    Assert.assertTrue(orders.size() == 2);
	}
	
	@Test
    public void testFieldDI() {
        List<Order> orders = fieldDI.getCustomerOrders(1l);
        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.size() == 2);
    }
	
	@Test
    public void testSetterDI() {
        List<Order> orders = setterDI.getCustomerOrders(1l);
        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.size() == 2);
    }
	
	@Test
    public void testCombined() {
        List<Order> ordersSetter = setterDI.getCustomerOrders(1l);
        List<Order> ordersConstructor = constructorDI.getCustomerOrders(1l);
        List<Order> ordersField = fieldDI.getCustomerOrders(1l);
        
        Assert.assertNotNull(ordersSetter);
        Assert.assertNotNull(ordersConstructor);
        Assert.assertNotNull(ordersField);
        Assert.assertTrue(ordersSetter.size() == 2 && ordersConstructor.size() == ordersSetter.size() && ordersField.size() == ordersSetter.size());
    }
	
	

}
