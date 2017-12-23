package com.baeldung.beaninjection.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.BeanConfig;
import com.baeldung.beaninjection.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfig.class, loader = AnnotationConfigContextLoader.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrderService() {
        Order order = orderService.getOrders(123);
        assertTrue(order.getOrderNumber()
            .equals("A1233"));
        assertTrue(order.getProductName()
            .equals("Product Name"));
        assertTrue(order.getPrice() == 123.50);
        assertNotNull("Additional Offer isn't null", order.getAdditionalOffers());
        if (order.getCustomer() != null) {
            assertTrue(order.getAdditionalOffers()
                .getOfferCode()
                .equals("ABC123"));
            assertTrue(order.getAdditionalOffers()
                .getProductRecommendation()
                .equals("Other Products"));
        }
        assertNotNull("Customer isn't null", order.getCustomer());
        if (order.getCustomer() != null) {
            assertTrue(order.getCustomer()
                .getName()
                .contains("Customer Name"));
            assertTrue(order.getCustomer()
                .getCustomerId() == 123);
            assertTrue(order.getCustomer()
                .getContactInfo()
                .contains("Contact Information of Customer"));
            assertTrue(order.getCustomer()
                .getEmail()
                .contains("customer@abc.com"));
        }
    }
}
