package com.baeldung.beaninjection.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.BeanConfig;
import com.baeldung.beaninjection.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfig.class, loader = AnnotationConfigContextLoader.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCustomerService() {
        Customer customer = customerService.getCustomer(123);
        assertTrue(customer.getName()
            .contains("Customer Name"));
        assertTrue(customer.getCustomerId() == 123);
        assertTrue(customer.getContactInfo()
            .contains("Contact Information of Customer"));
        assertTrue(customer.getEmail()
            .contains("customer@abc.com"));
    }
}
