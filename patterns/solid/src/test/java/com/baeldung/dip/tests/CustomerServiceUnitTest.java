package com.baeldung.dip.tests;

import com.baeldung.dip.daoimplementations.ListCustomerDao;
import com.baeldung.dip.daointerfaces.CustomerDao;
import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.services.CustomerService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class CustomerServiceUnitTest {

    private CustomerService customerService;

    @Before
    public void setUpCustomerServiceInstance() {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(new Customer(1, "John"), new Customer(2, "Susan")));
        customerService = new CustomerService(customerDao);
    }

    @Test
    public void givenCustomerServiceInstance_whenCalledFindById_thenCorrect() {
        assertThat(customerService.findById(0)).isInstanceOf(Optional.class);
    }

    @Test
    public void givenCustomerServiceInstance_whenCalledFindAll_thenCorrect() {
        assertThat(customerService.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void givenCustomerServiceInstance_whenCalledFindByIdWithNullCustomer_thenCorrect() {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(null, new Customer(2, "Susan")));
        customerService = new CustomerService(customerDao);

        Customer customer = customerService.findById(0).orElseGet(() -> new Customer(0, "Non-existing customer"));

        assertThat(customer.getName()).isEqualTo("Non-existing customer");
    }
}
