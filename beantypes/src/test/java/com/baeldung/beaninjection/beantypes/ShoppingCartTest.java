package com.baeldung.beaninjection.beantypes;

import com.baeldung.beaninjection.beantypes.Services.OrderService;
import com.baeldung.beaninjection.beantypes.Services.ShoppingCartService;
import com.baeldung.beaninjection.beantypes.config.ServicesConfig;
import com.baeldung.beaninjection.beantypes.models.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServicesConfig.class, loader = AnnotationConfigContextLoader.class)
public class ShoppingCartTest {

    @Autowired
    private ShoppingCartService cartService;

    @Test
    public void whenConstructorInjectionTypeCustomerInjected_thenRetrieveCustomer_withname(){
        Customer customer = cartService.GetRelatedCustomer();
        assertNotNull(customer!=null);
        assertTrue(customer.getFirstName().equals("tal"));
        assertTrue(customer.getLastName().equals("avissar"));
    }
}
