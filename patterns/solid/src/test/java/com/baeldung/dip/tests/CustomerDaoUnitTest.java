package com.baeldung.dip.tests;

import com.baeldung.dip.daoimplementations.ListCustomerDao;
import com.baeldung.dip.daointerfaces.CustomerDao;
import com.baeldung.dip.entities.Customer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class CustomerDaoUnitTest {

    @Test
    public void givenCustomerDaoInstance_whenCalledFindById_thenCorrect() {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(new Customer(1, "John")));

        assertThat(customerDao.findById(0)).isInstanceOf(Optional.class);
    }

    @Test
    public void givenCustomerDaoInstance_whenCalledFindAll_thenCorrect() {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(new Customer(1, "John"), new Customer(2, "Susan")));

        assertThat(customerDao.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void givenCustomerDaoInstance_whenCalledFindByIdWithNullCustomer_thenCorrect() {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(null, new Customer(2, "Susan")));

        Customer customer = customerDao.findById(0).orElseGet(() -> new Customer(0, "Non-existing customer"));

        assertThat(customer.getName()).isEqualTo("Non-existing customer");
    }
}
