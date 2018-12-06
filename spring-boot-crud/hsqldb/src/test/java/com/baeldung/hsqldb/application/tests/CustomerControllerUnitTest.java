package com.baeldung.hsqldb.application.tests;

import com.baeldung.hsqldb.application.controllers.CustomerController;
import com.baeldung.hsqldb.application.entities.Customer;
import com.baeldung.hsqldb.application.repositories.CustomerRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class CustomerControllerUnitTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private BindingResult bindingResult;
    
    @Mock
    private Model model;
    
    @InjectMocks
    private CustomerController customerController;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void whenCalledshowCustomerForm_thenCorrect() {
        Customer customer = new Customer("Julie", "julie@domain.com");
        
        assertThat(customerController.showCustomerForm(customer)).isEqualTo("addcustomer");
    }
    
    @Test
    public void whenCalledaddCustomerAndValidCustomer_thenCorrect() {
        Customer customer = new Customer("Julie", "julie@domain.com");
        
        when(bindingResult.hasErrors()).thenReturn(false);
        
        assertThat(customerController.addCustomer(customer, bindingResult, model)).isEqualTo("index");
    }
    
    @Test
    public void whenCalledaddCustomerAndInvaliCustomer_thenCorrect() {
        Customer customer = new Customer("Julie", "julie@domain.com");

        when(bindingResult.hasErrors()).thenReturn(true);
        
        assertThat(customerController.addCustomer(customer, bindingResult, model)).isEqualTo("addcustomer");
    }
}
