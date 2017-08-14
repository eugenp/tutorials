package com.baeldung.dependencyinjection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = TestConfig.class)
public class CustomerServiceConstructorBasedIntegrationTest {

    @Autowired
    private CustomerServiceConstructorBased customerService;
    
    @Test
    public void givenIdExists_whenGetCustomer_thenReturnsCustomer() {
        Customer customer = customerService.getCustomer(2L);
        
        assertEquals("customer2", customer.getName());
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void givenIdDoesNotExist_whenGetCustomer_thenThrowsException() {
        customerService.getCustomer(-1L);
    }
}
