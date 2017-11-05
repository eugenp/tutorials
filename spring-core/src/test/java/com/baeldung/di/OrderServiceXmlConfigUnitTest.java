package com.baeldung.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class OrderServiceXmlConfigUnitTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void givenApplicationContext_whenOrderServiceIsInstantiated_thenDependenciesShouldNotBeNull() {
        assertNotNull(orderService);
        assertNotNull(orderService.getUserService());
        assertNotNull(orderService.getProductService());
    }
}