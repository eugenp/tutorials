package com.baeldung.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.annotationTest.config.AppConfig;
import com.baeldung.spring.annotationTest.services.OrderService;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AnnotationBeanInjectionTest {
	
	@Autowired
    private OrderService orderService;

    @Test
    public void testOrderService() {
    	
    	assertNotNull(orderService);
    	orderService.saveOrder();
        
    }
    

}
