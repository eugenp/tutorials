package com.baeldung.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class OrderServiceAnnotationConfigUnitTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void givenApplicationConfig_whenOrderServiceIsInstantiated_thenDependenciesShouldNotBeNull() {
        assertNotNull(orderService);
        assertNotNull(orderService.getUserService());
        assertNotNull(orderService.getProductService());
    }

    @Configuration
    @ComponentScan(basePackages = {"com.baeldung.di"})
    static class ApplicationConfig {}
}