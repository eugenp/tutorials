package com.baeldung.dip.tests;

import com.baeldung.dip.daoimplementations.MapCustomerDao;
import com.baeldung.dip.daointerfaces.CustomerDao;
import com.baeldung.dip.entities.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class CustomerDaoUnitTest {

    private CustomerDao customerDao;

    @Before
    public void setUpCustomerDaoInstance() {
        var customers = new HashMap<Integer, Customer>();
        customers.put(1, new Customer("John"));
        customers.put(2, new Customer("Susan"));
        customerDao = new MapCustomerDao(customers);
    }

    @Test
    public void givenCustomerDaoInstance_whenCalledFindById_thenCorrect() {
        assertThat(customerDao.findById(1)).isInstanceOf(Optional.class);
    }

    @Test
    public void givenCustomerDaoInstance_whenCalledFindAll_thenCorrect() {
        assertThat(customerDao.findAll()).isInstanceOf(Map.class);
    }

    @Test
    public void givenCustomerDaoInstance_whenCalledFindByIdWithNullCustomer_thenCorrect() {
        var customers = new HashMap<Integer, Customer>();
        customers.put(1, null);
        CustomerDao customerDaoObject = new MapCustomerDao(customers);
        Customer customer = customerDaoObject.findById(1).orElseGet(() -> new Customer("Non-existing customer"));
        assertThat(customer.getName()).isEqualTo("Non-existing customer");
    }
}
